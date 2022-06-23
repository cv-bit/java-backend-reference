package com.dpi.publishingapi.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    // runs every time a custom exception is thrown
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(Exception exception) {
        CustomException customException = (CustomException) exception;

        return new ResponseEntity<>(
                new ErrorResponse(
                        customException.getStatus(),
                        customException.getMessage()
                ),
                customException.getStatus()
        );
    }
}
