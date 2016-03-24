package org.kaakaa.pptmuseum.event;

import org.kaakaa.pptmuseum.db.MongoDBClient;

/**
 * Created by kaakaa on 16/03/19.
 */
public interface Event<T> {
    MongoDBClient mongoDBClient = new MongoDBClient();

    T execute() throws EventException;
}
