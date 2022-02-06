package com.example.universe.simulator.entityservice.common.utils;

import com.example.universe.simulator.eventservice.entities.Event;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.UUID;

public final class TestUtils {

    private TestUtils() {}

    public static Event buildEvent(Clock clock) {
        return new Event(
            UUID.randomUUID(),
            "type",
            "data",
            OffsetDateTime.now(clock)
        );
    }
}
