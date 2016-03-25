package org.kaakaa.pptmuseum.event;

import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;

/**
 * Created by kaakaa on 16/03/19.
 */
public class EventExecuter {
    public static <T> T execute(Event<T> event) throws EventException {
        return event.execute();
    }
}
