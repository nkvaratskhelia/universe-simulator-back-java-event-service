package com.example.universe.simulator.eventservice.controllers;

import com.example.universe.simulator.common.dtos.EventDto;
import com.example.universe.simulator.eventservice.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService service;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<@NonNull EventDto> getList() {
        log.info("calling getList");
        return service.getList()
            .map(item -> new EventDto(item.type(), item.data(), item.time()));
    }
}
