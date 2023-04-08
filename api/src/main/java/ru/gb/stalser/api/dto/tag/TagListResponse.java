package ru.gb.stalser.api.dto.tag;

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
@Schema(description = "Список тегов", name = "TagList")
public class TagListResponse {
    @Schema(description = "Теги")
    private List<TagDto> tags;
}
