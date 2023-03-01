package ru.stalser.framework.autotestobjects;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.stalser.api.dto.auth.AuthRequestPassUpdate;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.gb.stalser.api.dto.board.BoardDto;

import java.time.LocalDateTime;

@RequiredArgsConstructor(staticName = "of")
@Data
public class BoardCreateRqDto {

    private final BoardDto boardDto;
    private String boardId;
    private LocalDateTime creationTime = LocalDateTime.now();
    private LocalDateTime updatedTime = LocalDateTime.now();
}
