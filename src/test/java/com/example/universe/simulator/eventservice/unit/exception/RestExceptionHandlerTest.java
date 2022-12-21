package com.example.universe.simulator.eventservice.unit.exception;

import com.example.universe.simulator.eventservice.common.abstractions.AbstractWebFluxTest;
import com.example.universe.simulator.eventservice.controllers.EventController;
import com.example.universe.simulator.eventservice.exception.RestExceptionHandler;
import com.example.universe.simulator.eventservice.services.EventService;
import com.example.universe.simulator.eventservice.types.ErrorCodeType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ProblemDetail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

// Exception handling is tested using EventController.
@WebFluxTest(EventController.class)
class RestExceptionHandlerTest extends AbstractWebFluxTest {

    @MockBean
    private EventService service;

    @Test
    void testUnknownException() {
        // given
        given(service.getList()).willThrow(RuntimeException.class);
        // when
        ProblemDetail result = webClient.get()
            .uri("/events")
            .exchange()
            .expectStatus().isEqualTo(ErrorCodeType.SERVER_ERROR.getHttpStatus())
            .expectBody(ProblemDetail.class)
            .returnResult()
            .getResponseBody();
        // then
        assertThat(result)
            .isNotNull()
            .extracting(ProblemDetail::getDetail)
            .isEqualTo(ErrorCodeType.SERVER_ERROR.toString());

        assertThat(result.getProperties())
            .isNotNull()
            .extractingByKey(RestExceptionHandler.TIMESTAMP_PROPERTY)
            .isNotNull();

        then(service).should().getList();
    }
}
