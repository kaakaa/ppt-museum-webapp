package org.kaakaa.pptmuseum.db.document;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
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
import java.util.stream.Collectors;

/**
 * Created by kaakaa on 16/02/13.
 */
@Entity
public class Slide {
    @Id
    private ObjectId id;
    @Property("title")
    private String title;
    @Property("description")
    private String description;
    @Embedded("tags")
    private List<String> tags = new ArrayList<>();

    @Embedded("thumbnail")
    private Resource thumbnail;
    @Embedded("pdf")
    private Resource pdfResource;
    @Embedded("powerpoint")
    private Resource powerpointResource;

    /**
     * default constructor
     */
    public Slide() {
    }

    public void setThumbnail(Resource file) {
        this.thumbnail = file;
    }

    public Resource getThumbnail() {
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

    public void setPowerpointResource(Resource doc) {
        this.powerpointResource = doc;
    }

    public Resource getPowerpointResource() {
        return this.powerpointResource;
    }

    public void setPdfResource(Resource doc) {
        this.pdfResource = doc;
    }

    public Resource getPDFResource() {
        return this.pdfResource;
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
        return ReflectionToStringBuilder.toStringExclude(this, "id", "thumbnail", "pdfResource", "powerpointResource").toString();
    }

    public JSONObject getMetaData() {
        JSONObject object = new JSONObject();

        object.put("title", this.title);
        object.put("description", this.description);
        object.put("tags", getTagsToString());

        return object;
    }
}
