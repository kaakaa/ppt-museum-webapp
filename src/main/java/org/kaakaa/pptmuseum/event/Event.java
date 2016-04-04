package org.kaakaa.pptmuseum.event;

import org.kaakaa.pptmuseum.db.MongoDBClient;
import org.slf4j.Logger;

/**
 * Created by kaakaa on 16/03/19.
 */
public interface Event<T> {
    MongoDBClient mongoDBClient = new MongoDBClient();

    T execute() throws EventException;

    default void beforeLogging(Logger logger){
        logger.info("The event started");
    }

    default void afterLogging(Logger logger) {
        logger.info("The event finised");
    }
}
