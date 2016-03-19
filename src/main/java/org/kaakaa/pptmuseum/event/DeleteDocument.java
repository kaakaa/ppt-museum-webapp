package org.kaakaa.pptmuseum.event;

import spark.Request;

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
