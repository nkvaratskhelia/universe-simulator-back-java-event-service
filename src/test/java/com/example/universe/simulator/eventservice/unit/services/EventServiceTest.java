package com.example.universe.simulator.eventservice.unit.services;

import com.example.universe.simulator.eventservice.common.utils.TestUtils;
import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.repositories.EventRepository;
import com.example.universe.simulator.eventservice.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Clock;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService service;

    @Test
    void testGetList() {
        // given
        Flux<Event> flux = Flux.just(
            TestUtils.buildEvent(Clock.systemDefaultZone())
        );

        given(repository.findAll()).willReturn(flux);
        // when
        Flux<Event> result = service.getList();
        // then
        StepVerifier.create(result)
            .expectNextSequence(flux.toIterable())
            .verifyComplete();

        then(repository).should().findAll();
    }
}
