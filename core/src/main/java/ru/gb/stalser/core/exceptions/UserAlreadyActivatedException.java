package ru.gb.stalser.core.exceptions;

public class UserAlreadyActivatedException extends RuntimeException {
    public UserAlreadyActivatedException(final String message) {
        super(message);
    }
}
