package org.kaakaa.pptmuseum.db.document;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by kaakaa on 16/02/13.
 */
@EqualsAndHashCode
@ToString(exclude = {"id", "tags", "thumbnail"})
@Entity
public class Slide {
    @Id
    private ObjectId id;
    @Property("title")
    private String title;
    @Property("description")
    private String description;
    @Property("thumbnail")
    private byte[] thumbnail;

    @Embedded("tags")
    private List<String> tags = new ArrayList<>();

    @Embedded("pdf")
    private Document pdfDocument;
    @Embedded("powerpoint")
    private Document powerpointDocument;

    /**
     * default contructor
     */
    public Slide() {
    }

    public void setThumbnail(byte[] file) {
        this.thumbnail = file;
    }

    public byte[] getThumbnail() {
        return this.thumbnail;
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTags(List<String> tags) {
        this.tags = tags.stream().map(t -> t.trim()).filter(t -> t.length() != 0).collect(Collectors.toList());
    }

    public List<String> getTags() {
        return this.tags;
    }

    public String getTagsToString() {
        return String.join(",", this.tags);
    }

    public String getTime() {
        Instant instant = Instant.ofEpochSecond(this.id.getTimestamp());
        LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneId.SHORT_IDS.get("JST")));
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public void setPowerpointDocument(Document doc) {
        this.powerpointDocument = doc;
    }

    public Document getPowerpointDocument() {
        return this.powerpointDocument;
    }

    public void setPdfDocument(Document doc) {
        this.pdfDocument = doc;
    }

    public Document getPDFDocument() {
        return this.pdfDocument;
    }
}
