package Service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CertificadoService {

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

    public static void generate(String text) {
        try {
            int width = 300;
            int height = 300;

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);

            Path path = Path.of("out/qr_code.png");
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

            System.out.println("QR Code created at: " + "out/qr_code.png");

        } catch (Exception e) {
            System.out.println("Error generating QR Code");
            e.printStackTrace();
        }
    }
}