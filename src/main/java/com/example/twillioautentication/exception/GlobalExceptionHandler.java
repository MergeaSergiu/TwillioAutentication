package com.example.twillioautentication.exception;


import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponseContainer = new ErrorResponse();

        errorResponseContainer.setHttpStatusCode(HttpStatus.CONFLICT.value());
        errorResponseContainer.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponseContainer, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistsException ex){
        ErrorResponse errorResponseContainer = new ErrorResponse();

        errorResponseContainer.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponseContainer.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponseContainer, HttpStatus.NOT_FOUND);
    }
}
