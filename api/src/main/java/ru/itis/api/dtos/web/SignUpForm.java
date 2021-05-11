package ru.itis.api.dtos.web;

import lombok.Data;
import ru.itis.api.validation.annotations.ValidPassword;

import javax.validation.constraints.*;

@Data
public class SignUpForm {

    @NotBlank(message = "${validation.emptyField}")
    @Email(message = "${validation.email}")
    private String email;

    @NotBlank(message = "${validation.emptyField}")
    private String name;

    @NotBlank(message = "${validation.emptyField}")
    @ValidPassword(message = "validation.invalidPassword")
    private String password;

    @NotBlank(message = "${validation.emptyField}")
    private String verifyPassword;

}
