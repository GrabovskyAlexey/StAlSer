package ru.gb.stalser.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Schema(description = "Новый пароль", name = "RequestNewPass")
public class RequestNewPass {
    @NotEmpty(message = "Токен не может быть пустым")
    @Schema(description = "Токен для смены пароля")
    private String token;

    @NotEmpty(message = "Пароль пользователя не может быть пустым")
    @Size(min = 5, message = "Пароль пользователя должен содержать не мение 5-ти символов")
    @Schema(description = "Новый пароль пользователя")
    private String newPassword;

    @NotEmpty(message = "Имя не может быть пустым")
    @Schema(description = "Имя для смены пароля")
    private String userName;
}
