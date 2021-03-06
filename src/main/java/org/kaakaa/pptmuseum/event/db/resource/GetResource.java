package org.kaakaa.pptmuseum.event.db.resource;

import org.kaakaa.pptmuseum.db.ResourceType;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

/**
 * Created by kaakaa on 16/03/24.
 */
public class GetResource implements Event<Resource> {
    private final Request request;
    private final ResourceType type;

    Logger logger = LoggerFactory.getLogger(getClass());

    public GetResource(Request rq, ResourceType type) {
        this.request = rq;
        this.type = type;
    }

    @Override
    public Resource execute() throws EventException {
        beforeLogging(logger);
        Resource result = mongoDBClient.getResource(this.request.params(":id"), type);
        afterLogging(logger);

        return result;
    }
}
