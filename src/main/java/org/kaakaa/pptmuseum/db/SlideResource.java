package org.kaakaa.pptmuseum.db;

import java.util.Arrays;

/**
 * Created by kaakaa on 16/03/20.
 */
public enum SlideResource {
    PDF("application/pdf"),
    PPT("application/vnd.ms-powerpoint"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    PPTM("application/vnd.ms-powerpoint.presentation.macroEnabled.12"),
    THUMBNAIL("image/png"),
    UNKNOWN("application/octet-stream");

    private final String contentType;

    SlideResource(String type) {
        this.contentType = type;
    }

    public String getContentType() {
        return this.contentType;
    }

    public static SlideResource toSlideResource(String contentType) {
        return Arrays.asList(values()).stream()
                .filter(s -> s.getContentType().equals(contentType))
                .findFirst().orElse(UNKNOWN);
    }
}
