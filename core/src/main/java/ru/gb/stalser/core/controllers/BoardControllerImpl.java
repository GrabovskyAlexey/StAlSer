package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Page<BoardDto>> getAllBoards(int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return ResponseEntity.ok(
                boardService.findAll(pageIndex-1, 10).map(boardMapper::mapToDto));
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
