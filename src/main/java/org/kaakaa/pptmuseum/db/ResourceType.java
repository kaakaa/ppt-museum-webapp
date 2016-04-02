package org.kaakaa.pptmuseum.db;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by kaakaa on 16/03/20.
 */
public enum ResourceType {
    PDF("application/pdf", "pdf"),
    PPT("application/vnd.ms-powerpoint", "ppt"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx"),
    PPTM("application/vnd.ms-powerpoint.presentation.macroEnabled.12", "pptm"),
    THUMBNAIL("image/png", "png"),
    UNKNOWN("application/octet-stream", "txt");

    private final String contentType;
    private final String ext;

    private static final Logger logger = LoggerFactory.getLogger(ResourceType.class);

    ResourceType(String type, String ext) {
        this.contentType = type;
        this.ext = ext;
    }

    public String getContentType() {
        return this.contentType;
    }

    public static ResourceType toSlideResource(FileItem item) {
        String contentType = item.getContentType();
        ResourceType type = Arrays.asList(values()).stream()
                .filter(s -> s.getContentType().equals(contentType))
                .findFirst().orElse(UNKNOWN);

        logger.info("Get resource type by content-type. resource-type: {}. content-type: {}", type, contentType);
        // return if not UNKNOWN type
        if(!UNKNOWN.equals(type)){
            return type;
        }

        // if getting unknown resource type, find resource type by file name.
        String filename = Arrays.asList(item.getHeaders().getHeader("content-disposition").split(";")).stream().filter(s -> s.trim().startsWith("filename")).findFirst().orElse("unknown");
        return getContentTypeByFileName(filename);
    }

    private static ResourceType getContentTypeByFileName(String filename) {
        final String LITERAL_DOUBLE_QUOTATION = "\"";
        ResourceType type =  Arrays.asList(values()).stream()
                .filter(s -> filename.endsWith(s.getExt() + LITERAL_DOUBLE_QUOTATION))
                .findFirst().orElse(UNKNOWN);

        logger.info("Get resource type by filename. resource-type: {}. filename: {}", type, filename);
        return type;
    }

    public String getExt() {
        return this.ext;
    }

}
