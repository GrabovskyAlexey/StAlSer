package ru.gb.stalser.core.exceptions;

public class DifferentEmailException extends RuntimeException{
    public DifferentEmailException(String message) {
        super(message);
    }
}
