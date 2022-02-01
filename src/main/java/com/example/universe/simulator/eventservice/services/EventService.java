package com.example.universe.simulator.eventservice.services;

import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    public Flux<Event> getList() {
        return repository.findAll();
    }
}
