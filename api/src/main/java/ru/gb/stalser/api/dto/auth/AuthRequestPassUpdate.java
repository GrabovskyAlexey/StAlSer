package ru.gb.stalser.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Schema(description = "Запрос смены пароля", name = "AuthRequestPassUpdate")
public class AuthRequestPassUpdate {

    @NotEmpty(message = "Пароль пользователя не может быть пустым")
    @Schema(description = "Старый пароль пользователя")
    private String oldPassword;

    @NotEmpty(message = "Пароль пользователя не может быть пустым")
    @Size(min = 3, message = "Пароль пользователя должен содержать не мение 5-ти символов")
    @Schema(description = "Новый пароль пользователя")
    private String newPassword;
}
