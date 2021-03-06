package ru.itis.api.dtos.web;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class BoardCreatingForm {

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 50, message = "Название не может превышать 50 символов")
    private String name;

    private String type;

}
