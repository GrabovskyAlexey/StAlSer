package ru.gb.stalser.api.dto.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.task.TaskDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация об активности", name = "Activity")
public class ActivityDto {
    @Schema(description = "Идентификатор активности", example = "null")
    @Min(value = 1)
    private Long id;

    @Schema(description = "Статус активности")
    @JsonProperty("status")
    private String status;

    @NotEmpty(message = "Информация об активности пользователя не должна быть пустой")
    @Schema(description = "Информация о активности")
    @JsonProperty("activityInfo")
    private String activityInfo;

    @NotEmpty(message = "У активности должна быть задача к которой она относится")
    @Schema(description = "Задача к которой относится активность")
    @JsonProperty("task")
    private TaskDto task;

    @NotEmpty(message = "У активности должен быть пользователь к которой она относится")
    @Schema(description = "Пользователь к которому относится активность")
    @JsonProperty("user")
    private Long userId;

}
