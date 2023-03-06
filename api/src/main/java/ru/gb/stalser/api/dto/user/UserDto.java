package ru.gb.stalser.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные пользователя", name = "User")
public class UserDto {

    @Schema(description = "Идентификационный номер пользователя", example = "1")
    @Min(value = 1)
    private Long id;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotEmpty(message = "Email не может быть пустым")
    @Schema(description = "Email пользователя", example = "Example@example.com")
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

    @Schema(description = "Список пользователей с ролями")
    @JsonProperty("boardId")
    private List<Long> boardId;
}
