package ru.gb.stalser.core.services.interfaces;


import org.springframework.data.domain.Page;
import ru.gb.stalser.core.entity.Board;



public interface BoardService {

    Page<Board> findAll(int pageIndex, int pageSize);

    Board findById(Long id);

    Board save(Board board);

    void updateBoard(Board board);

    Boolean existsBoardById(Long id);
}
