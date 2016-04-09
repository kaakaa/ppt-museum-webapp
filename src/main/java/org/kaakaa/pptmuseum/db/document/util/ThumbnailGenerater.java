package org.kaakaa.pptmuseum.db.document.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by kaakaa on 16/03/24.
 */
public class ThumbnailGenerater {
    private static final Logger logger = LoggerFactory.getLogger(ThumbnailGenerater.class);

    /**
     * <p>Generate thumbnail image from PDF bytes.</p>
     * @param bytes PDF bytes
     * @return thumbnail image bytes
     */
    public static byte[] generate(byte[] bytes) {
        try(PDDocument pdDoc = PDDocument.load(new ByteArrayInputStream(bytes)); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDFRenderer render = new PDFRenderer(pdDoc);
            BufferedImage bufferedImage = render.renderImage(0);

            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("Generate thumbnail image error: ", e);
        }
        return new byte[1024];
    }
}
