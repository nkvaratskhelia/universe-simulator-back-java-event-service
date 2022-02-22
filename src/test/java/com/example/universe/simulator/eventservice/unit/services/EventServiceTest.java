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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService service;

    @Test
    void testGetList() {
        // given
        List<Event> entities = List.of(
            TestUtils.buildEvent(Clock.systemDefaultZone())
        );

        given(repository.findAll()).willReturn(Flux.fromIterable(entities));
        // when
        Flux<Event> result = service.getList();
        // then
        StepVerifier.create(result)
            .expectNextSequence(entities)
            .verifyComplete();

        then(repository).should().findAll();
    }

    @Test
    void testAdd_duplicateEntity() {
        // given
        Event entity = TestUtils.buildEvent(Clock.systemDefaultZone());

        given(repository.findByTypeAndDataAndTime(any(), any(), any()))
            .willReturn(Mono.just(entity));
        // when
        Mono<Event> result = service.add(entity);
        // then
        StepVerifier.create(result)
            .expectNext(entity)
            .verifyComplete();

        then(repository).should().findByTypeAndDataAndTime(entity.type(), entity.data(), entity.time());
        then(repository).should((never())).save(any());
    }

    @Test
    void testAdd() {
        // given
        Event entity = TestUtils.buildEvent(Clock.systemDefaultZone());

        given(repository.findByTypeAndDataAndTime(any(), any(), any()))
            .willReturn(Mono.empty());
        given(repository.save(any())).willReturn(Mono.just(entity));
        // when
        Mono<Event> result = service.add(entity);
        // then
        StepVerifier.create(result)
            .expectNext(entity)
            .verifyComplete();

        then(repository).should().findByTypeAndDataAndTime(entity.type(), entity.data(), entity.time());
        then(repository).should().save(entity);
    }
}
