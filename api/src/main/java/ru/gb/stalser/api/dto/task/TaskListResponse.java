package ru.gb.stalser.api.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Список задач", name = "TaskList")
public class TaskListResponse {
    @Schema(description = "Задачи")
    private List<TaskDto> tasks;
}
