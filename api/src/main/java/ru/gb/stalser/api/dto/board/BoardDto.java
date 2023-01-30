package ru.gb.stalser.api.dto.board;

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
@Schema(description = "Информация о доске", name = "Board")
public class BoardDto {

    @Schema(description = "Идентификатор доски", example = "1")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Название доски не может быть пустым")
    @Size(min = 3, max = 100, message = "Название доски должно содержать от 3 до 100 символов. Проверьте длину.")
    @Schema(description = "Название доски")
    @JsonProperty("boardName")
    private String boardName;

    @NotEmpty(message = "Описание доски не может быть пустым")
    @Size(min = 3, message = "Описание доски должно быть минимум 3 символа")
    @Schema(description = "Описание доски")
    @JsonProperty("boardDesc")
    private String boardDesc;

    @NotEmpty(message = "Сокращенное название доски не может быть пустым")
    @Size(min = 3, max = 10, message = "Сокращенное название доски должно содержать от 3 до 10 символов. Проверьте длину.")
    @Schema(description = "Сокращенное название доски")
    @JsonProperty("boardName")
    private String boardAlias;

    @NotEmpty(message = "Маркер активности доски не может быть пустым")
    @Schema(description = "Маркер активности доски")
    @JsonProperty("isActive")
    private Boolean isActive;
}
