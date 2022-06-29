package com.dpi.publishingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

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

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ConstraintViolationException exception) {

        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
