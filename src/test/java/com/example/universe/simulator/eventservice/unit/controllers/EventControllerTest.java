package com.example.universe.simulator.eventservice.unit.controllers;

import com.example.universe.simulator.eventservice.common.abstractions.AbstractWebFluxTest;
import com.example.universe.simulator.eventservice.common.utils.TestUtils;
import com.example.universe.simulator.eventservice.controllers.EventController;
import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.services.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Clock;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@WebFluxTest(EventController.class)
class EventControllerTest extends AbstractWebFluxTest {

    @MockBean
    private EventService service;

    @Test
    void testGetList() {
        // given
        Flux<Event> flux = Flux.just(
            TestUtils.buildEvent(Clock.systemUTC())
        );

        given(service.getList()).willReturn(flux);
        // when
        Flux<Event> result = webClient.get()
            .uri("/event/get-list")
            .exchange()
            .expectStatus().isOk()
            .returnResult(Event.class)
            .getResponseBody();
        // then
        StepVerifier.create(result)
            .expectNextSequence(flux.toIterable())
            .verifyComplete();

        then(service).should().getList();
    }
}
