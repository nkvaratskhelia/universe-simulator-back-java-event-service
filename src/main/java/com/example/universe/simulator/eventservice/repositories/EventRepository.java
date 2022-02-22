package com.example.universe.simulator.eventservice.repositories;

import com.example.universe.simulator.eventservice.entities.Event;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface EventRepository extends R2dbcRepository<Event, UUID> {

    Mono<Event> findByTypeAndDataAndTime(String type, String data, OffsetDateTime time);
}
