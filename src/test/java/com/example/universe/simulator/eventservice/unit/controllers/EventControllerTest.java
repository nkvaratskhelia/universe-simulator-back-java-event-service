package com.example.universe.simulator.eventservice.unit.controllers;

import static org.mockito.BDDMockito.given;

import com.example.universe.simulator.common.dtos.EventDto;
import com.example.universe.simulator.eventservice.common.abstractions.AbstractWebFluxTest;
import com.example.universe.simulator.eventservice.common.utils.TestUtils;
import com.example.universe.simulator.eventservice.controllers.EventController;
import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.services.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.util.List;

@WebFluxTest(EventController.class)
class EventControllerTest extends AbstractWebFluxTest {

    @MockitoBean
    private EventService service;

    @Test
    void testGetList() {
        // given
        List<Event> entities = List.of(
            TestUtils.buildEvent(Clock.systemUTC())
        );
        List<EventDto> dtos = entities.stream()
            .map(item -> new EventDto(item.type(), item.data(), item.time()))
            .toList();

        given(service.getList()).willReturn(Flux.fromIterable(entities));

        // when
        Flux<EventDto> result = webClient.get()
            .uri("/events")
            .exchange()
            .expectStatus().isOk()
            .returnResult(EventDto.class)
            .getResponseBody();

        // then
        StepVerifier.create(result)
            .expectNextSequence(dtos)
            .verifyComplete();
    }
}
