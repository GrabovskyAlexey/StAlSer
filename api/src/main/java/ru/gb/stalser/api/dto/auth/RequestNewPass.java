package ru.gb.stalser.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("token")
    private String token;

    @NotEmpty(message = "Пароль пользователя не может быть пустым")
    @Size(min = 8, message = "Пароль пользователя должен содержать не мение 8-ми символов")
    @Schema(description = "Новый пароль пользователя")
    @JsonProperty("newPassword")
    private String newPassword;

    @NotEmpty(message = "Имя не может быть пустым")
    @Schema(description = "Имя для смены пароля")
    @JsonProperty("userEmail")
    private String userEmail;
}
