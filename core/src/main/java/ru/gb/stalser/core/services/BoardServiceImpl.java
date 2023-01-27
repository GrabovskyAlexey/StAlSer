package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Board;
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
    public Board findById(Long id) {

        return boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Board id=" + id + "{} not found"));
    }

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public void updateBoardFromDto(Board boardDto) {

        Board board = boardRepository.findById(boardDto.getId()).orElseThrow(() -> new EntityNotFoundException("Product id = " + boardDto.getId() + " not found"));

        board.setBoardAlias(boardDto.getBoardAlias());
        board.setBoardName(boardDto.getBoardName());
        board.setBoardDesc(boardDto.getBoardDesc());
        board.setIsActive(boardDto.getIsActive());
        boardRepository.save(board);
    }
}
