package org.kaakaa.pptmuseum.db.document;

import org.kaakaa.pptmuseum.db.ResourceType;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by kaakaa on 16/02/17.
 */
@Embedded
public class Resource {
    @Property("type")
    private String contentType;
    @Property("file")
    private byte[] file;
    @Property("ext")
    private String ext;

    public Resource() {
    }

    public Resource(ResourceType resource, byte[] file) {
        this.contentType = resource.getContentType();
        this.ext = resource.getExt();
        this.file = file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getExt() {
        return this.ext;
    }
}
