package com.example.universe.simulator.eventservice.exception;

import com.example.universe.simulator.common.dtos.ErrorDto;
import com.example.universe.simulator.eventservice.types.ErrorCodeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    private ResponseEntity<ErrorDto> buildResponse(ErrorCodeType errorCode, Exception exception) {
        log.error("", exception);

        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(new ErrorDto(errorCode.toString(), Instant.now()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorDto> handleUnknownException(Exception exception) {
        return buildResponse(ErrorCodeType.SERVER_ERROR, exception);
    }
}
