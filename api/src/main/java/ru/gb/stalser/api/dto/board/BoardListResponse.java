package ru.gb.stalser.api.dto.board;

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
@Schema(description = "Список досок", name = "BoardList")
public class BoardListResponse {

    @Schema(description = "Доски")
    private List<BoardDto> boards;
}
