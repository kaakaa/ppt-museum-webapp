package org.kaakaa.pptmuseum.event.document;

import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import spark.Request;

/**
 * Created by kaakaa on 16/03/24.
 */
public class UpdateDocument implements Event {
    private final Request request;

    public UpdateDocument(Request rq) {
        this.request = rq;
    }

    @Override
    public Object execute() throws EventException {
        return mongoDBClient.updateSlideInfo(request.params(":id"), request.queryParams("title"), request.queryParams("desc"), request.queryParams("tags"));
    }
}
