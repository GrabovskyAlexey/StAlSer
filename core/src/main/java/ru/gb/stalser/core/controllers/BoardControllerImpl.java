package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.api.dto.board.BoardListResponse;
import ru.gb.stalser.core.controllers.interfaces.BoardController;
import ru.gb.stalser.core.mappers.BoardMapper;
import ru.gb.stalser.core.services.interfaces.BoardService;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/boards")
@RequiredArgsConstructor
public class BoardControllerImpl implements BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    @Override
    public BoardListResponse getAllBoards(Principal principal) {
        return BoardListResponse.builder()
                .boards(boardService.findAllByUsername(principal).stream()
                        .map(boardMapper::mapToDto)
                        .collect(Collectors.toList()))
                .build();
    }


    @Override
    public BoardDto getBoardById(Long id, Principal principal) {
        return boardMapper.mapToDto(boardService.findById(id));
    }

    @Override
    public BoardDto addBoard(BoardDto board, Principal principal) {
        return boardMapper.mapToDto(
                boardService.save(boardMapper.mapFromDto(board), principal));
    }

    @Override
    public void updateBoard(Long id, BoardDto board, Principal principal) {
        boardService.updateBoard(boardMapper.mapFromDto(board), principal);
    }
}
