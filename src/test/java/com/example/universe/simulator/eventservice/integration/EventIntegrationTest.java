package com.example.universe.simulator.eventservice.integration;

import com.example.universe.simulator.common.dtos.EventDto;
import com.example.universe.simulator.eventservice.common.utils.TestUtils;
import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Clock;

import static org.awaitility.Awaitility.await;

class EventIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EventRepository repository;

    @Value("${app.rabbitmq.event-queue}")
    private String eventQueue;

    @Test
    void test() {
        // -----------------------------------should return empty list-----------------------------------

        // when
        Flux<Event> result = webClient.get()
            .uri("/event/get-list")
            .exchange()
            .returnResult(Event.class)
            .getResponseBody();
        // then
        StepVerifier.create(result)
            .expectNextCount(0)
            .verifyComplete();

        // -----------------------------------add entity-----------------------------------

        EventDto dto = TestUtils.buildEventDto(Clock.systemUTC());
        rabbitTemplate.convertAndSend(eventQueue, dto);

        // -----------------------------------wait for entity to be added-----------------------------------

        await().until(() -> repository.count().blockOptional().orElse(0L) > 0);

        // -----------------------------------should return list with 1 element-----------------------------------

        // when
        result = webClient.get()
            .uri("/event/get-list")
            .exchange()
            .returnResult(Event.class)
            .getResponseBody();
        // then
        StepVerifier.create(result)
            .expectNextCount(1)
            .verifyComplete();
    }
}
