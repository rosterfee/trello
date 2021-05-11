package ru.itis.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

}
