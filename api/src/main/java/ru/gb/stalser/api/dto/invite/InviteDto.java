package ru.gb.stalser.api.dto.invite;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.board.BoardDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о приглашении", name = "Invite")
public class InviteDto {
    @Schema(description = "Идентификатор приглашения", example = "null")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Email не должен быть пустым")
    @Schema(description = "Email для отправки приглашения")
    @JsonProperty("email")
    private String email;

    @NotEmpty(message = "Код для приглашения не должен быть пустым")
    @Schema(description = "Код для подтверждения приглашения")
    @JsonProperty("inviteCode")
    private String inviteCode;

    @NotEmpty(message = "Статус приглашения не должен быть пустым")
    @Schema(description = "Статус приглашения")
    @JsonProperty("status")
    private InviteStatus status;

    @NotEmpty(message = "Дата истечения приглашения не должна быть пустой")
    @Schema(description = "Дата, когда приглашение станет неактивным")
    @JsonProperty("expirationDate")
    private Instant expirationDate;

    @NotEmpty(message = "У приглашения должна быть доска к которой оно относится")
    @Schema(description = "Доска к которой относится приглашение")
    @JsonProperty("board")
    private BoardDto board;

    @Schema(description = "Зарегистрированный пользователь, которому было отправлено приглашение")
    @JsonProperty("user")
    private Long userId;
}
