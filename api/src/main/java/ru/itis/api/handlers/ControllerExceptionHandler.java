package ru.itis.api.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.itis.api.dtos.rest.ValidationErrorsResponse;
import ru.itis.api.exceptions.NoPrivilegesException;
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.exceptions.ValidationException;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(
            ValidationException exception,
            WebRequest webRequest) {

        ValidationErrorsResponse response = ValidationErrorsResponse.builder()
                .validationErrors(exception.getValidationErrors())
                .message(exception.getMessage())
                    .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NoPrivilegesException.class)
    public ResponseEntity<Object> handleNoPrivilegesException (
            NoPrivilegesException exception,
            WebRequest webRequest) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException (
            AuthenticationException exception,
            WebRequest webRequest) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
