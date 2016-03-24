package org.kaakaa.pptmuseum.event.resource;

import org.kaakaa.pptmuseum.db.SlideResource;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import spark.Request;

/**
 * Created by kaakaa on 16/03/24.
 */
public class GetResource implements Event<Resource> {
    private final Request request;
    private final SlideResource type;

    public GetResource(Request rq, SlideResource type) {
        this.request = rq;
        this.type = type;
    }

    @Override
    public Resource execute() throws EventException {
        return mongoDBClient.getResource(this.request.params(":id"), type);
    }
}
