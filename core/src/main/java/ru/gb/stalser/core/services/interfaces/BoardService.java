package ru.gb.stalser.core.services.interfaces;


import org.springframework.transaction.annotation.Transactional;
import ru.gb.stalser.core.entity.Board;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board findById(Long id);

    Board save(Board board);

    @Transactional
    void updateBoardFromDto(Board board);
}
