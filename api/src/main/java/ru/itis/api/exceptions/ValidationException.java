package ru.itis.api.exceptions;

import ru.itis.api.validation.ValidationErrors;

public class ValidationException extends RuntimeException {

    String message;
    ValidationErrors validationErrors;

    public ValidationException(String message, ValidationErrors validationErrors) {
        this.message = message;
        this.validationErrors = validationErrors;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }
}
