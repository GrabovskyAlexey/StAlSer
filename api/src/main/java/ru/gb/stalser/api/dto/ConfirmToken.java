package ru.gb.stalser.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class ConfirmToken {
    private String email;
    private String code;
    private TokenType type;

    public enum TokenType {
        REGISTER, INVITE;

        public static TokenType getByName(final String name){
            if(Objects.isNull(name)){
                return null;
            }
            return valueOf(name);
        }
    }

    public boolean isValidToken(){
        return Objects.nonNull(email) && Objects.nonNull(code) && Objects.nonNull(type);
    }
}
