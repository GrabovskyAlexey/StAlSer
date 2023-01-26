package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.repositories.BoardRepository;
import ru.gb.stalser.core.services.interfaces.BoardService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;


    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public void updateBoardFromDto(Board boardDto) {

        Board board = findById(boardDto.getId()).orElse(new Board());

        board.setBoardAlias(boardDto.getBoardAlias());
        board.setBoardName(boardDto.getBoardName());
        board.setBoardDesc(boardDto.getBoardDesc());
        board.setIsActive(boardDto.getIsActive());
        save(board);
    }
}
