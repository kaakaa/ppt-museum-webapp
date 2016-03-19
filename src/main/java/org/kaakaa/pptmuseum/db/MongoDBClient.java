package org.kaakaa.pptmuseum.db;

import org.bson.types.ObjectId;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.db.mongo.MongoConnectionHelper;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kaakaa on 16/02/14.
 */
public class MongoDBClient {
    /**
     * Morphia
     */
    private Datastore datastore = MongoConnectionHelper.getMorphiaInstance();

    /**
     * <p>Upload slide to db</p>
     *
     * @param slide Slide model
     * @return return key
     */
    public Object upload(Slide slide) {
        return datastore.save(slide);
    }

    /**
     * <p>Get all slide information</p>
     *
     * @return all slide information
     */
    public List<Slide> searchAll(int offset, int limit) {
        return datastore.createQuery(Slide.class).order("-_id").offset((offset - 1) * limit).limit(limit).asList();
    }

    /**
     * <p>Get the number of uploaded slides.</p>
     *
     * @return all slides size
     */
    public int allSlideSize() {
        return datastore.createQuery(Slide.class).asList().size();
    }

    /**
     * <p>Get pdf document</p>
     *
     * @param id id
     * @return Resource Model
     */
    public Resource getPDF(String id) {
        return datastore.get(Slide.class, new ObjectId(id)).getPDFResource();
    }

    /**
     * <p>Get powerpoint dcoument</p>
     * @param id id
     * @return Resource Model
     */
    public Resource getPowerpoint(String id) {
        return datastore.get(Slide.class, new ObjectId(id)).getPowerpointResource();
    }

    /**
     * <p>DELETE uploaded slides</p>
     *
     * @param id id
     */
    public void delete(String id) {
        datastore.delete(Slide.class, new ObjectId(id));
    }

    public Resource getThumbnail(String id) {
        Slide slide = datastore.get(Slide.class, new ObjectId(id));
        return slide.getThumbnail();
    }

    /**
     * <p>SEARCH slides by tags </p>
     * @param keyword search keyword
     * @return searched slide list
     */
    public List<Slide> searchFilteredKeyword(String keyword) {
        List<String> keywordList = new ArrayList<>();
        keywordList.add(keyword);
        return datastore.createQuery(Slide.class).field("tags").hasAnyOf(keywordList).asList();
    }

    /**
     * <p>Update Slide Information</p>
     *
     * @param id    slide id
     * @param title slide title - editing
     * @param desc  slide description - editing
     * @param tags  slide tags - editing
     */
    public void updateSLideInfo(String id, String title, String desc, String tags) {
        Query<Slide> updateQuery = datastore.createQuery(Slide.class).field("_id").equal(new ObjectId(id));

        List<String> tagList = Arrays.asList(tags.split(",")).stream().map(t -> t.trim()).filter(t -> t.length() > 0).collect(Collectors.toList());
        UpdateOperations<Slide> ops = datastore.createUpdateOperations(Slide.class).set("title", title).set("description", desc).set("tags", tagList);

        datastore.update(updateQuery, ops);
    }
}
