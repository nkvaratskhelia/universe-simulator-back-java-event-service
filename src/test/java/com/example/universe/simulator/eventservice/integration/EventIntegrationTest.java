package com.example.universe.simulator.eventservice.integration;

import com.example.universe.simulator.eventservice.entities.Event;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class EventIntegrationTest extends AbstractIntegrationTest {

    @Test
    void test() {
        // -----------------------------------should return empty list-----------------------------------

        // when
        Flux<Event> result = webClient.get()
            .uri("/event/get-list")
            .exchange()
            .expectStatus().isOk()
            .returnResult(Event.class)
            .getResponseBody();
        // then
        StepVerifier.create(result)
            .expectNextCount(0)
            .verifyComplete();
    }
}
