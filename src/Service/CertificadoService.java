package Service;

import Entity.Certificado;
import Entity.Discente;
import Entity.Oportunidade;
import Entity.Usuario;
import Repository.impl.CertificadoRepositoryImpl;
import Repository.impl.OportunidadeRepositoryImpl;
import Repository.impl.UsuarioRepositoryImpl;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


public class CertificadoService {

        private final CertificadoRepositoryImpl banco;
        private final OportunidadeRepositoryImpl oportunidadeRepository;
        private final UsuarioRepositoryImpl usuarioRepository;

        public CertificadoService(CertificadoRepositoryImpl banco,
                                  OportunidadeRepositoryImpl oportunidadeRepository,
                                  UsuarioRepositoryImpl usuarioRepository) {
            this.banco = banco;
            this.oportunidadeRepository = oportunidadeRepository;
            this.usuarioRepository = usuarioRepository;
        }


        public static String verificarAutenticidade(String caminho) throws Exception {
        File arquivo = new File(caminho);
        BufferedImage imagem = ImageIO.read(arquivo);

        BinaryBitmap bitmap = new BinaryBitmap(
                new HybridBinarizer(
                        new BufferedImageLuminanceSource(imagem)
                )
        );

        Result resultado = new MultiFormatReader().decode(bitmap);
        return resultado.getText();
    }

    public static void generate(String text, String path) {
        try {
            int width = 300;
            int height = 300;

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
            Path filePath = Paths.get(path + "qrcode.png");
            Files.createDirectories(filePath.getParent());
            MatrixToImageWriter.writeToPath(matrix, "PNG", filePath);

            System.out.println("QR Code gerado em: " + filePath);
        } catch (Exception e) {
            System.out.println("Erro ao gerar QR Code");
            e.printStackTrace();
        }
        }

    public void criarCertificado(Long oportunidadeId, Long discenteId, Integer horas) {
        Oportunidade o = oportunidadeRepository.buscaPorId(oportunidadeId);
        Usuario u = usuarioRepository.buscarPorId(discenteId);

        if (!(u instanceof Discente discente)) {
            throw new RuntimeException("Usuário não é um discente");
        }

        Certificado certificado = new Certificado(oportunidadeId, discenteId, horas);

        String path = "certificados/" + u.getNome() + "/" + o.getTitulo() + "/";

        generate(certificado.getUuid_hash(), path);
        certificado.setPath(path + "qrcode.png");
        certificado.assinar();

        banco.salvar(certificado);
        System.out.println("Certificado gerado para " + discente.getNome() + " em " + path);
    }
}