package ru.gb.stalser.core.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(final String message) {
        super(message);
    }
}
