package org.kaakaa.pptmuseum.db.document;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by kaakaa on 16/02/17.
 */
@Embedded
public class Document {
    @Property("file")
    private byte[] file;
    @Property("type")
    private String contentType;

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public void setContentType(String type) {
        this.contentType = type;
    }

    public String getContentType() {
        return this.contentType;
    }
}
