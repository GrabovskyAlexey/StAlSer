package ru.gb.stalser.core.services.interfaces;


import org.springframework.transaction.annotation.Transactional;
import ru.gb.stalser.core.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<Board> findAll();

    Optional<Board> findById(Long id);

    Board save(Board board);

    @Transactional
    void updateBoardFromDto(Board board);
}
