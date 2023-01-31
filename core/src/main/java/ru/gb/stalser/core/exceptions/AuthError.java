package ru.gb.stalser.core.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthError {

    private List<String> messages;
    private Date date;

    public AuthError(List<String> messages) {
        this.messages = messages;
        this.date = new Date();
    }

    public AuthError(String message) {
        this(List.of(message));
    }

    public AuthError(String... messages) {
        this(Arrays.asList(messages));
    }
}
