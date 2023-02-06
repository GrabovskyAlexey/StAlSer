package ru.gb.stalser.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "Ответ аутентификации", name = "AuthResponse")
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {

        this.token = token;
    }
}
