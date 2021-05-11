package ru.itis.web.utils;

import org.springframework.validation.BindingResult;
import ru.itis.api.validation.ValidationErrors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationErrorsGenerator {

    public ValidationErrors generateValidationErrors(BindingResult bindingResult) {

        Map<String, String> fieldsErrors = new HashMap<>();
        List<String> globalErrors = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            fieldsErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        bindingResult.getGlobalErrors().forEach(globalError -> {
            globalErrors.add(globalError.getDefaultMessage());
        });

        return ValidationErrors.builder()
                .fieldsErrors(fieldsErrors)
                .globalErrors(globalErrors)
                .build();
    }

}
