package com.example.universe.simulator.eventservice.unit.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.universe.simulator.eventservice.common.abstractions.AbstractWebFluxTest;
import com.example.universe.simulator.eventservice.controllers.EventController;
import com.example.universe.simulator.eventservice.exception.RestExceptionHandler;
import com.example.universe.simulator.eventservice.services.EventService;
import com.example.universe.simulator.eventservice.types.ErrorCodeType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebFluxTest(EventController.class)
class RestExceptionHandlerTest extends AbstractWebFluxTest {

    @MockitoBean
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
    }
}
