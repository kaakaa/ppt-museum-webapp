package org.kaakaa.pptmuseum.event;

/**
 * Created by kaakaa on 16/03/19.
 */
public class EventException extends Exception {
    public EventException(String message, Throwable t) {
        super(message, t);
    }
}
