package ru.itis.api.validation.annotations;


import ru.itis.api.validation.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}