package ru.gb.stalser.core.exceptions;

public class PasswordNotConfirmedException extends RuntimeException{
    public PasswordNotConfirmedException(String message) {
        super(message);
    }
}
