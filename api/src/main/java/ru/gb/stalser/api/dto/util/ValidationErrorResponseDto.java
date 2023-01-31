package ru.gb.stalser.api.dto.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@RequiredArgsConstructor
@Schema(description = "List of validation error", name = "ValidationErrorResponse")
public class ValidationErrorResponseDto {
    @Schema(description = "Список ошибок валидации")
    @JsonProperty("errors")
    private final List<ValidationError> errors;
    @Schema(description = "Дата и время возникновения ошибок", example = "2022-09-15T19:35:49.926529Z")
    @JsonProperty("time")
    private final Instant time = Instant.now();
}
