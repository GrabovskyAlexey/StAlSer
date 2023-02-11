package ru.gb.stalser.core.exceptions;

public class InviteWasExpiredException extends RuntimeException{
    public InviteWasExpiredException(String message) {
        super(message);
    }
}
