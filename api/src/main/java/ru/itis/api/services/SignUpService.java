package ru.itis.api.services;

import ru.itis.api.dtos.web.SignUpForm;
import ru.itis.api.enums.Role;
import ru.itis.api.exceptions.UserNotFoundException;

public interface SignUpService {

    void signUpWithRole(SignUpForm signUpForm, Role role);

    void confirmUserByConfirmCode(String confirmCode) throws UserNotFoundException;

    boolean userWithSuchEmailExists(String email);

}
