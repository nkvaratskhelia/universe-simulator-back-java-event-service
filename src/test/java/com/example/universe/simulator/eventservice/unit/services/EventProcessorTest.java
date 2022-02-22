package com.example.universe.simulator.eventservice.unit.services;

import com.example.universe.simulator.common.dtos.EventDto;
import com.example.universe.simulator.eventservice.common.utils.TestUtils;
import com.example.universe.simulator.eventservice.entities.Event;
import com.example.universe.simulator.eventservice.services.EventProcessor;
import com.example.universe.simulator.eventservice.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Clock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class EventProcessorTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventProcessor eventProcessor;

    @Test
    void testProcess() {
        // given
        EventDto event = TestUtils.buildEventDto(Clock.systemDefaultZone());

        given(eventService.add(any())).willReturn(Mono.empty());
        // when
        eventProcessor.process(event);
        // then
        then(eventService).should().add(
            new Event(null, event.type(), event.data(), event.time())
        );
    }
}
