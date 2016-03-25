package org.kaakaa.pptmuseum.db.document.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kaakaa on 16/03/24.
 */
public class ThumbnailGenerater {

    /**
     * <p>Generate thumbnail image from PDF bytes.</p>
     * @param bytes PDF bytes
     * @return thumbnail image bytes
     */
    public static byte[] generate(byte[] bytes) {
        InputStream input = new ByteArrayInputStream(bytes);
        try {
            PDDocument pdDoc = PDDocument.load(input);
            PDFRenderer render = new PDFRenderer(pdDoc);
            BufferedImage bufferedImage = render.renderImage(0);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[1024];
    }
}
