package ru.gb.stalser.api.dto.comment;

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
@Schema(description = "Информация о Комментарие", name = "Comment")
public class CommentDto {

        @Schema(description = "Id Комментария", example = "1")
        @Min(value = 1)
        private Long id;

        @NotEmpty(message = "У комментария должна быть задача к которой он относится")
        @Schema(description = "Задача к которой относится комментарий")
        @JsonProperty("taskDto")
        private TaskDto task;

        @NotEmpty(message = "У комментария должен быть создатель к которой он относится")
        @Schema(description = "Пользователь к которому относится комментарий")
        @JsonProperty("userDto")
                private Long userId;

        @NotEmpty(message = "Поле комментарий не может быть пустым")
        @Schema(description = "Текст комментария")
        @JsonProperty("commentText")
        private String commentText;



}