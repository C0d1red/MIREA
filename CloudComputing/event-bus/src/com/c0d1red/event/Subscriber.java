package com.c0d1red.event;

public class Subscriber {
    private final long id;
    private final String eventName;
    private final Event event;

    public Subscriber(long id, String eventName, Event event) {
        this.id = id;
        this.eventName = eventName;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public void callEvent() {
        event.execute();
    }
}
