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
@Schema(description = "Activity information", name = "Activity")
public class ActivityDto {
    @Schema(description = "Activity id", example = "null")
    @Min(value = 1)
    private Long id;

    @Schema(description = "Status of activity")
    @JsonProperty("status")
    private String status;

    @NotEmpty(message = "Информация об активности пользователя не должна быть пустой")
    @Schema(description = "Information about activity")
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
