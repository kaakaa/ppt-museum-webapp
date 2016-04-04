package org.kaakaa.pptmuseum.db.document.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        try(PDDocument pdDoc = PDDocument.load(new ByteArrayInputStream(bytes))) {
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
