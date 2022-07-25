package com.example.universe.simulator.eventservice.repositories;

import com.example.universe.simulator.eventservice.entities.Event;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface EventRepository extends ReactiveCrudRepository<Event, UUID> {

    Mono<Event> findByTypeAndDataAndTime(String type, String data, OffsetDateTime time);
}
