package ru.gb.stalser.core.services.interfaces;


import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.User;

import java.util.List;

public interface BoardService {

    List<Board> findAll();
    List<Board> findAllBoardWithUser(User user);

    Board findById(Long id);

    Board save(Board board);

    void updateBoard(Board board);

    Boolean existsBoardById(Long id);
}
