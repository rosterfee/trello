package ru.itis.api.exceptions;

public class NoPrivilegesException extends RuntimeException {

    public NoPrivilegesException(String message) {
        super(message);
    }
}
