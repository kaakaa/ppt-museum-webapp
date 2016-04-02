package org.kaakaa.pptmuseum.event;

import org.kaakaa.pptmuseum.db.MongoDBClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kaakaa on 16/03/19.
 */
public interface Event<T> {
    MongoDBClient mongoDBClient = new MongoDBClient();

    Logger logger = LoggerFactory.getLogger(Event.class);

    T execute() throws EventException;

    default void beforeLogging(){
        logger.info("Start Event");
    }
}
