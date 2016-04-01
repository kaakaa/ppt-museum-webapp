package org.kaakaa.pptmuseum.db;

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

    ResourceType(String type, String ext) {
        this.contentType = type;
        this.ext = ext;
    }

    public String getContentType() {
        return this.contentType;
    }

    public static ResourceType toSlideResource(String contentType) {
        return Arrays.asList(values()).stream()
                .filter(s -> s.getContentType().equals(contentType))
                .findFirst().orElse(UNKNOWN);
    }

    public String getExt() {
        return this.ext;
    }
}
