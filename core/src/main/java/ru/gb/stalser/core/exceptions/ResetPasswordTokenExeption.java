package ru.gb.stalser.core.exceptions;

public class ResetPasswordTokenExeption extends RuntimeException {
    public ResetPasswordTokenExeption(String message){
        super(message);
    }
}
