package ru.stalser.framework.helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutomationException extends Exception {

    private final String message;

    public AutomationException(String message) {
        this.message = message;
    }
}
