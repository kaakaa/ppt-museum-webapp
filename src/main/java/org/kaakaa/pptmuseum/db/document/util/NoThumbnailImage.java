package org.kaakaa.pptmuseum.db.document.util;

import org.kaakaa.pptmuseum.db.ResourceType;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by kaakaa on 16/03/24.
 */
public class NoThumbnailImage {
    private static Resource noThumbnailResource;

    private static final Logger logger = LoggerFactory.getLogger(NoThumbnailImage.class);

    /**
     * <p>Get default resource model</p>
     *
     * @return resource model
     */
    public static Resource get() {
        if (noThumbnailResource == null) {
            initialize();
        }
        return noThumbnailResource;
    }

    /**
     * <p>Make NoThumbnailImage Resource.</p>
     */
    private static void initialize() {
        byte[] bytes = readDefaultImage();
        noThumbnailResource = new Resource(ResourceType.THUMBNAIL, bytes);
    }

    /**
     * <p>Read default no thumbnail image.</p>
     *
     * @return image bytes
     * @throws IOException can't read
     */
    private static byte[] readDefaultImage() {
        BufferedImage image = null;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            image = ImageIO.read(NoThumbnailImage.class.getResource("/no_image.png"));
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("Read default image error: ", e);
        }
        return new byte[1024];
    }
}
