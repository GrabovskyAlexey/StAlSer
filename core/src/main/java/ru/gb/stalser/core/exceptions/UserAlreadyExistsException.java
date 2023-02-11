package ru.gb.stalser.core.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
