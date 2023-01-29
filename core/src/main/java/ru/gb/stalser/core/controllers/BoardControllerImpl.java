package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.core.controllers.interfaces.BoardController;
import ru.gb.stalser.core.mappers.BoardMapper;
import ru.gb.stalser.core.services.interfaces.BoardService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/boards")
@RequiredArgsConstructor
public class BoardControllerImpl implements BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    @Override
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        return ResponseEntity.ok(
                boardService.findAll().stream()
                        .map(boardMapper::mapToDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<BoardDto> getBoardById(Long id) {
        return ResponseEntity.ok(boardMapper.mapToDto(boardService.findById(id)));
    }

    @Override
    public ResponseEntity<BoardDto> addBoard(BoardDto board) {
        return ResponseEntity.ok(
                boardMapper.mapToDto(
                        boardService.save(boardMapper.mapFromDto(board))
                )
        );
    }

    @Override
    public void updateBoard(Long id, BoardDto board) {
        boardService.updateBoard(boardMapper.mapFromDto(board));
    }
}
