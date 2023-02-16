package ru.gb.stalser.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Запрос смены пароля", name = "AuthRequestPassUpdate")
public class AuthRequestPassUpdate {
    private String login;
    private String oldPassword;
    private String newPassword;
}
