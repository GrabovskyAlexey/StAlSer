package ru.gb.stalser.api.dto.restriction;

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
public class RestrictionDto {
    @Schema(description = "Идентификатор ограничения", example = "1")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Название ограничения не может быть пустым")
    @Size(min = 3, max = 30, message = "Название ограничения должно содержать от 3 до 30 символов. Проверьте длину.")
    @Schema(description = "Название ограничения")
    @JsonProperty("restrictionName")
    private String restrictionName;

    @Size(max = 30, message = "Описание ограничения должно содержать до 100 символов. Проверьте длину.")
    @Schema(description = "Описание ограничения")
    @JsonProperty("restrictionDesc")
    private String restrictionDesc;
}
