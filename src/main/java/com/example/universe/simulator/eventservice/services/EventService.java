package com.example.universe.simulator.eventservice.services;

import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    public Flux<@NonNull Event> getList() {
        return repository.findAll();
    }

    @Transactional
    public Mono<@NonNull Event> add(Event entity) {
        return repository.findByTypeAndDataAndTime(entity.type(), entity.data(), entity.time())
            .switchIfEmpty(Mono.defer(() -> repository.save(entity)));
    }
}
