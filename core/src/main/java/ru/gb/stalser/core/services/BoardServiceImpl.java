package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.repositories.BoardRepository;
import ru.gb.stalser.core.services.interfaces.BoardService;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    @Override
    public Page<Board> findAll(int pageIndex, int pageSize) {
        return boardRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public Board findById(Long id) {

        return boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Board id=" + id + "{} not found"));
    }

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public void updateBoard(Board boardDto) {

        boardRepository.save(boardDto);
    }

    @Override
    public Boolean existsBoardById(Long id) {
        return boardRepository.existsBoardById(id);
    }
}
