package org.kaakaa.pptmuseum.event.db.slide;

import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import spark.Request;

/**
 * Created by kaakaa on 16/03/19.
 */
public class DeleteSlide implements Event {

    private final Request request;

    public DeleteSlide(Request rq) {
        this.request = rq;
    }

    @Override
    public Object execute() throws EventException {
        return mongoDBClient.delete(this.request.params("id"));
    }
}
