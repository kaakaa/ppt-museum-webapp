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

    public Resource() {}
    public Resource(ResourceType resource, byte[] file){
        this.contentType = resource.getContentType();
        this.file = file;
    }

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
