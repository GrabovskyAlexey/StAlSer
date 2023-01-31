package ru.gb.stalser.core.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class StAlSerError {

    private List<String> messages;
    private Date date;

    public StAlSerError(List<String> messages) {
        this.messages = messages;
        this.date = new Date();
    }

    public StAlSerError(String message) {
        this(List.of(message));
    }

    public StAlSerError(String... messages) {
        this(Arrays.asList(messages));
    }
}
