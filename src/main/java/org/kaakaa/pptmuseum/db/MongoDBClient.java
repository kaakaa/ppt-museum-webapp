package org.kaakaa.pptmuseum.db;

import org.bson.types.ObjectId;
import org.kaakaa.pptmuseum.db.document.Document;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.db.mongo.MongoConnectionHelper;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

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
     * @param document Document model
     * @return return key
     */
    public Object upload(Slide slide, Document document) {
        Key<Slide> result = datastore.save(slide);
        document.setId(result.getId().toString());
        return datastore.save(document);
    }

    /**
     * <p>Get all slide information</p>
     *
     * @return all slide information
     */
    public List<Slide> searchAll(int offset, int limit) {
        return datastore.createQuery(Slide.class).order("-_id").offset((offset - 1) * limit).limit(limit).asList();
    }

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
        return datastore.get(Document.class, new ObjectId(id));
    }

    /**
     * <p>DELETE uploaded slides</p>
     *
     * @param id id
     */
    public void delete(String id) {
        datastore.delete(Slide.class, new ObjectId(id));
        Query<Document> documentQuery = datastore.createQuery(Document.class).filter("id =", new ObjectId(id));
        datastore.delete(documentQuery);
    }

    public byte[] getThumbnail(String id) {
        Slide slide = datastore.get(Slide.class, new ObjectId(id));
        return slide.getThumbnail();
    }
}
