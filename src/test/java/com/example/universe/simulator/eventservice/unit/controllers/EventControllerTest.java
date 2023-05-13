package com.example.universe.simulator.eventservice.unit.controllers;

import com.example.universe.simulator.common.dtos.EventDto;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@WebFluxTest(EventController.class)
class EventControllerTest extends AbstractWebFluxTest {

    @MockBean
    private EventService service;

    @Test
    void testGetList() {
        // given
        List<Event> entities = List.of(
            TestUtils.buildEvent(Clock.systemUTC())
        );
        List<EventDto> dtos = entities.stream()
            .map(item -> new EventDto(item.type(), item.data(), item.time()))
            .collect(Collectors.toList());

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
