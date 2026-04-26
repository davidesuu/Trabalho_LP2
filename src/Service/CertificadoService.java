package Service;

import Entity.Certificado;
import Entity.Discente;
import Entity.Oportunidade;
import Repository.impl.CertificadoRepositoryImpl;
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

public class CertificadoService {

    private final CertificadoRepositoryImpl banco;

    public CertificadoService(CertificadoRepositoryImpl banco) {
        this.banco = banco;
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

    public static void generate(String text, Discente d) {
        try {
            int width = 300;
            int height = 300;

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
            Path path = Path.of("certificados/" + d.getNome() + "/" + "qrcode.png");
            Files.createDirectories(path);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

            System.out.println("QR Code created at: " + "out/qr_code.png");

        } catch (Exception e) {
            System.out.println("Error generating QR Code");
            e.printStackTrace();
        }
    }

    public void criarCertificado(Oportunidade oportunidade, Discente discente,
                               Integer horas){

        // uuid_hash = UUID.randomUUID().toString();
        Certificado certificado = new Certificado(oportunidade, discente, horas);
        generate(certificado.getUuid_hash(), discente);
        banco.salvar(certificado);
    }
}