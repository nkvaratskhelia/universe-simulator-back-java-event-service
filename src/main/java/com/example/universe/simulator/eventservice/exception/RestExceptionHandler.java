package com.example.universe.simulator.eventservice.exception;

import com.example.universe.simulator.eventservice.types.ErrorCodeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    public static final String TIMESTAMP_PROPERTY = "timestamp";

    @ExceptionHandler(Exception.class)
    private Mono<ProblemDetail> handleUnknownException(Exception exception) {
        return buildResponse(ErrorCodeType.SERVER_ERROR, exception);
    }

    private Mono<ProblemDetail> buildResponse(ErrorCodeType errorCode, Exception exception) {
        log.error("", exception);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(errorCode.getHttpStatus(), errorCode.toString());
        problemDetail.setProperty(TIMESTAMP_PROPERTY, Instant.now());

        return Mono.just(problemDetail);
    }
}
