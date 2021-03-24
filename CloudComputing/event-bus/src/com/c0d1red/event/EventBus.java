package com.c0d1red.event;

import com.c0d1red.util.IdGenerator;

import java.util.List;

public class EventBus {
    private final IdGenerator idGenerator;

    public EventBus() {
        idGenerator = new IdGenerator();
    }

    public long subscribe(List<Subscriber> subscribers, String eventName, Event event) {
        long id = idGenerator.getNextId();
        Subscriber subscriber = new Subscriber(id, eventName, event);
        subscribers.add(subscriber);
        return id;
    }

    public boolean unsubscribe(List<Subscriber> subscribers, String eventName, long id) {
        return subscribers.removeIf(subscriber -> subscriber.getEventName().equals(eventName) && subscriber.getId() == id);
    }

    public void publish(List<Subscriber> subscribers, String eventName) {
        subscribers.stream()
                .filter(subscriber -> subscriber.getEventName().equals(eventName))
                .forEach(Subscriber::callEvent);
    }
}
