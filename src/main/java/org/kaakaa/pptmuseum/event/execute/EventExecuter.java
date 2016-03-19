package org.kaakaa.pptmuseum.event.execute;

import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;

/**
 * Created by kaakaa on 16/03/19.
 */
public class EventExecuter {
    public static void execute(Event event) throws EventException {
        event.execute();
    }
}
