package ru.gb.stalser.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные пользователя", name = "User")
public class UserDto {

    @Schema(description = "Идентификационный номер пользователя", example = "1")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "E-mail пользователя")
    @Size(min = 5, max = 100, message = "E-mail пользователя должен содержать не мение пяти символов")
    @Schema(description = "E-mail пользователя",example = "example@example.com")
    private String email;

    @NotEmpty(message = "Логин пользователя не может быть пустым")
    @Size(min = 3, message = "Логин пользователя должен содержать не мение 3-х символов")
    @Schema(description = "Логин пользователя")
    private String login;

    @NotEmpty(message = "Отображаемое имя пользователя не может быть пустым")
    @Size(min = 3, message = "Отображаемое имя пользователя должно содержать не мение 3-х символов")
    @Schema(description = "Отображаемое имя пользователя")
    private String displayName;

    @NotEmpty(message = "Поле телеграмм пользователя не может быть пустым")
    @Size(min = 2, message = "Отображаемое логин в телеграмм должен содержать не мение 2-х символов")
    @Schema(description = "Отображаемое логин в телеграмм")
    private String telegram;
}
