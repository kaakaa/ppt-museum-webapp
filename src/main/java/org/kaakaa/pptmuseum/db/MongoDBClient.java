package org.kaakaa.pptmuseum.db;

import org.bson.types.ObjectId;
import org.kaakaa.pptmuseum.db.document.Document;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.db.mongo.MongoConnectionHelper;
import org.mongodb.morphia.Datastore;

import java.util.ArrayList;
import java.util.List;

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
     * @param slide    Slide model
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
     * <p>Get pdf contents</p>
     *
     * @param id id
     * @return Document Model
     */
    public Document getPDF(String id) {
        return datastore.get(Slide.class, new ObjectId(id)).getPDFDocument();
    }

    public Document getPowerpoint(String id) {
        return datastore.get(Slide.class, new ObjectId(id)).getPowerpointDocument();
    }

    /**
     * <p>DELETE uploaded slides</p>
     *
     * @param id id
     */
    public void delete(String id) {
        datastore.delete(Slide.class, new ObjectId(id));
    }

    public byte[] getThumbnail(String id) {
        Slide slide = datastore.get(Slide.class, new ObjectId(id));
        return slide.getThumbnail();
    }

    public List<Slide> searchFilteredKeyword(String keyword) {
        List<String> keywordList = new ArrayList<>();
        keywordList.add(keyword);
        return datastore.createQuery(Slide.class).field("tags").hasAnyOf(keywordList).asList();
    }

}
