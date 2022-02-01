package com.example.universe.simulator.eventservice.controllers;

import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("event")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService service;

    @GetMapping("get-list")
    public Flux<Event> getList() {
        log.info("calling getList");
        return service.getList();
    }
}
