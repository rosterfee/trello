package ru.itis.api.validation.validators;

import org.springframework.beans.BeanWrapperImpl;
import ru.itis.api.validation.annotations.PasswordsMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

    private String password;
    private String verifyPassword;

    public void initialize(PasswordsMatch constraintAnnotation) {
        password = constraintAnnotation.password();
        verifyPassword = constraintAnnotation.verifyPassword();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {

        String passwordValue = (String) new BeanWrapperImpl(value)
                .getPropertyValue(password);
        String verifyPasswordValue = (String) new BeanWrapperImpl(value)
                .getPropertyValue(verifyPassword);

        return passwordValue.equals(verifyPasswordValue);
    }

}
