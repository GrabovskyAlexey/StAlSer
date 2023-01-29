package ru.gb.stalser.core.services.interfaces;


import ru.gb.stalser.core.entity.Board;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board findById(Long id);

    Board save(Board board);

    void updateBoard(Long id, Board board);
}
