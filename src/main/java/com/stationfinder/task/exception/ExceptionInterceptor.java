package com.stationfinder.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ChargingStationException.class)
    public final ResponseEntity<Object> handleAllExceptions(ChargingStationException ex) {
        ChargingStationExceptionSchema exceptionResponse =
                new ChargingStationExceptionSchema(
                        ex.getMessage(), ex.getDetails(), ex.getHint());
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
