package com.c0d1red;

import com.c0d1red.event.EventBus;
import com.c0d1red.event.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String FIRST_EVENT_NAME = "event_1";
    private static final String SECOND_EVENT_NAME = "event_2";
    private static final String THIRD_EVENT_NAME = "event_3";

    public static void main(String[] args) {
        testEventBus();
    }

    private static void testEventBus() {
        EventBus eventBus = new EventBus();
        List<Subscriber> subscribers = new ArrayList<>();

        eventBus.subscribe(subscribers, FIRST_EVENT_NAME, () -> System.out.format("Hello from first %s\n", FIRST_EVENT_NAME));
        eventBus.subscribe(subscribers, FIRST_EVENT_NAME, () -> System.out.format("Hello from second %s\n", FIRST_EVENT_NAME));
        long secondEventId = eventBus.subscribe(subscribers, SECOND_EVENT_NAME, () -> System.out.format("Hello from %s\n", SECOND_EVENT_NAME));
        eventBus.subscribe(subscribers, THIRD_EVENT_NAME, () -> System.out.format("Hello from %s\n", THIRD_EVENT_NAME));

        eventBus.unsubscribe(subscribers, SECOND_EVENT_NAME, secondEventId);

        eventBus.publish(subscribers, FIRST_EVENT_NAME);
        eventBus.publish(subscribers, SECOND_EVENT_NAME);
        eventBus.publish(subscribers, THIRD_EVENT_NAME);
    }
}
