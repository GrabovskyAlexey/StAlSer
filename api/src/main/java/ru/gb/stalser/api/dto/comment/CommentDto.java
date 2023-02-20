package ru.gb.stalser.api.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.task.TaskDto;
import ru.gb.stalser.api.dto.user.UserDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о Комментарие", name = "Comment")
public class CommentDto {

    @Schema(description = "Id Комментария", example = "1")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "У комментария должна быть задача к которой он относится")
    @Schema(description = "Задача к которой относится комментарий")
    @JsonProperty("task")
    private TaskDto task;

    @NotEmpty(message = "У комментария должен быть создатель к которему он относится")
    @Schema(description = "Пользователь к которому относится комментарий")
    @JsonProperty("author")
    private UserDto author;

    @NotEmpty(message = "Поле комментарий не может быть пустым")
    @Schema(description = "Текст комментария")
    @JsonProperty("commentText")
    private String commentText;


}