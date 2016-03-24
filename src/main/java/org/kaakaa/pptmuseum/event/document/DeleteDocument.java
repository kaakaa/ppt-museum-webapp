package org.kaakaa.pptmuseum.event.document;

import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import spark.Request;

import java.util.function.Consumer;

/**
 * Created by kaakaa on 16/03/19.
 */
public class DeleteDocument implements Event {

    private final Request request;

    public DeleteDocument(Request rq) {
        this.request = rq;
    }

    @Override
    public void execute() throws EventException {
        mongoDBClient.delete(this.request.params("id"));
    }
}
