package ru.gb.stalser.core.exceptions;

public class InviteWithoutBoardException extends RuntimeException {
    public InviteWithoutBoardException(String message) {
        super(message);
    }
}
