package ru.gb.stalser.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmToken {
    private String email;
    private String code;
    private Type type;

    enum Type {
        REGISTER, INVITE;
    }
}
