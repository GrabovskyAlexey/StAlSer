package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.core.controllers.interfaces.BoardController;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.mappers.BoardMapper;
import ru.gb.stalser.core.services.interfaces.BoardService;
import ru.gb.stalser.core.services.interfaces.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/boards")
@RequiredArgsConstructor
public class BoardControllerImpl implements BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final UserService userService;

    @Override
    public ResponseEntity<List<BoardDto>> getAllBoards(Principal principal) {
        User user = userService.findByLogin(principal.getName());
        return ResponseEntity.ok(
                boardService.findAllBoardWithUser(user).stream()
                        .map(boardMapper::mapToDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<BoardDto> getBoardById(Long id, Principal principal) {
        return ResponseEntity.ok(boardMapper.mapToDto(boardService.findById(id)));
    }

    @Override
    public ResponseEntity<BoardDto> addBoard(BoardDto board, Principal principal) {
        return ResponseEntity.ok(
                boardMapper.mapToDto(
                        boardService.save(boardMapper.mapFromDto(board))
                )
        );
    }

    @Override
    public void updateBoard(Long id, BoardDto board, Principal principal) {
        boardService.updateBoard(boardMapper.mapFromDto(board));
    }
}
