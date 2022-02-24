package com.example.universe.simulator.eventservice.services;

import com.example.universe.simulator.common.dtos.EventDto;
import com.example.universe.simulator.eventservice.entities.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProcessor {

    private final EventService eventService;

    @RabbitListener(queues = "${app.rabbitmq.event-queue}")
    public void process(EventDto event) {
        log.info("received {}", event);

        eventService.add(
            new Event(null, event.type(), event.data(), event.time())
        ).subscribe(result -> log.info("processed {}", result));
    }
}
