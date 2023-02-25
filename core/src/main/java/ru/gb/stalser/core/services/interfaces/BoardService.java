package ru.gb.stalser.core.services.interfaces;


import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.User;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<Board> findAll();
    List<Board> findAllByUsersContaining(User user);

    Board findById(Long id);

    Board save(Board board);

    void updateBoard(Board board);

    Boolean existsBoardById(Long id);
}
