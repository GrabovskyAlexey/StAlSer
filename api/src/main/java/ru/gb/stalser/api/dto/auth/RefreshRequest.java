package ru.gb.stalser.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Schema(description = "Запрос нового токена", name = "RefreshRequest")
public class RefreshRequest {

    @NotEmpty(message = "Токен не может быть пустым")
    @Schema(description = "Токен обновления")
    private String refreshToken;
}
