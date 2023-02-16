package ru.gb.stalser.core.exceptions;

public class IncorrectConfirmTokenException extends RuntimeException {
    public IncorrectConfirmTokenException(final String message) {
        super(message);
    }
}
