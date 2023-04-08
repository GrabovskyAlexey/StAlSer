package ru.gb.stalser.api.dto.comment;

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
@Schema(description = "Список комментариев", name = "CommentsList")
public class CommentListResponse {
    @Schema(description = "Комментарии")
    private List<CommentDto> comments;
}
