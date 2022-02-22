package com.example.universe.simulator.eventservice.common.utils;

import com.example.universe.simulator.common.dtos.EventDto;
import com.example.universe.simulator.eventservice.entities.Event;
import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.UUID;

@UtilityClass
public class TestUtils {

    public EventDto buildEventDto(Clock clock) {
        return new EventDto(
            "type",
            "data",
            OffsetDateTime.now(clock)
        );
    }

    public Event buildEvent(Clock clock) {
        return new Event(
            UUID.randomUUID(),
            "type",
            "data",
            OffsetDateTime.now(clock)
        );
    }
}
