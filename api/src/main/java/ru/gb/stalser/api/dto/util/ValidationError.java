package ru.gb.stalser.api.dto.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Validation error info", name = "ValidationError")
public class ValidationError {
    @Schema(description = "Имя поля в котором возникла ошибка", example ="fieldName")
    @JsonProperty("fieldName")
    private String fieldName;

    @Schema(description = "Сообщение об ошибке", example ="Field Name must not be null")
    @JsonProperty("message")
    private String message;
}
