package com.example.universe.simulator.eventservice.unit.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.example.universe.simulator.eventservice.common.utils.TestUtils;
import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.repositories.EventRepository;
import com.example.universe.simulator.eventservice.services.EventService;
import org.jspecify.annotations.NonNull;
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
        Flux<@NonNull Event> result = service.getList();

        // then
        StepVerifier.create(result)
            .expectNextSequence(entities)
            .verifyComplete();
    }

    @Test
    void testAdd_duplicateEntity() {
        // given
        Event entity = TestUtils.buildEvent(Clock.systemDefaultZone());

        given(repository.findByTypeAndDataAndTime(entity.type(), entity.data(), entity.time()))
            .willReturn(Mono.just(entity));

        // when
        Mono<@NonNull Event> result = service.add(entity);

        // then
        StepVerifier.create(result)
            .expectNext(entity)
            .verifyComplete();

        then(repository).should((never())).save(any());
    }

    @Test
    void testAdd() {
        // given
        Event entity = TestUtils.buildEvent(Clock.systemDefaultZone());

        given(repository.findByTypeAndDataAndTime(entity.type(), entity.data(), entity.time()))
            .willReturn(Mono.empty());
        given(repository.save(entity)).willReturn(Mono.just(entity));

        // when
        Mono<@NonNull Event> result = service.add(entity);

        // then
        StepVerifier.create(result)
            .expectNext(entity)
            .verifyComplete();
    }
}
