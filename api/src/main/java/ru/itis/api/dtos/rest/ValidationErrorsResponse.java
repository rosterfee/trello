package ru.itis.api.dtos.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.api.validation.ValidationErrors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationErrorsResponse {

    private ValidationErrors validationErrors;
    private String message;

}
