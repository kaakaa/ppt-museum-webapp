package org.kaakaa.pptmuseum.event;

import org.kaakaa.pptmuseum.db.MongoDBClient;

/**
 * Created by kaakaa on 16/03/19.
 */
public interface Event {
    MongoDBClient mongoDBClient = new MongoDBClient();

    void execute() throws EventException;
}
