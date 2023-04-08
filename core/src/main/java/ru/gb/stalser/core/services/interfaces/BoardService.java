package ru.gb.stalser.core.services.interfaces;


import ru.gb.stalser.core.entity.Board;

import java.security.Principal;
import java.util.List;

public interface BoardService {

    List<Board> findAll();

    List<Board> findAllByUsername(Principal principal);

    Board findById(Long id);

    Board save(Board board, Principal principal);

    Board updateBoard(Board board, Principal principal);

    Boolean existsBoardById(Long id);
}
