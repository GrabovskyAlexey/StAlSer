package ru.gb.stalser.api.dto.tag;

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
@Schema(description = "Информация о теге", name = "Tag")
public class TagDto {
    @Schema(description = "Идентификатор тега", example = "1")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Имя тега не может быть пустым")
    @Size(min = 3, max = 100, message = "Название тега должно содержать от 3 до 100 символов. Проверьте длинну.")
    @Schema(description = "Имя тега")
    @JsonProperty("tagName")
    private String tagName;
}
