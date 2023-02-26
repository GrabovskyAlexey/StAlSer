package ru.gb.stalser.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Новый пароль", name = "RequestNewPass")
public class RequestNewPass {
    private String token;
    private String newPassword;
}
