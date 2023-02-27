package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.repositories.BoardRepository;
import ru.gb.stalser.core.services.interfaces.BoardService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public List<Board> findAllBoardWithUser(User user) {
        return boardRepository.findAllBoardWithUser(user);
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
