package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.repositories.BoardRepository;
import ru.gb.stalser.core.services.interfaces.BoardService;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserServiceImpl userService;


    @Override
    @Cacheable(value = "AllUserBoard")
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    @Cacheable(value = "userBoardByUser", key = "{#root.methodName, #principal.name}")
    public List<Board> findAllByUsername(Principal principal){

        User user = userService.findByLogin(principal.getName());
        return user.getBoards();

    }

    @Override
    @Cacheable(value = "userBoard", key = "{#id}")
    public Board findById(Long id) {

        return boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Board id=" + id + "{} not found"));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "userBoard", key = "{#result.id}")
            },
            evict = {
                    @CacheEvict(value = "userBoardByUser", key = "{#root.methodName, #principal.name}"),
                    @CacheEvict(value = "AllUserBoard")
            }
    )
    public Board save(Board board, Principal principal) {
        return boardRepository.save(board);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "userBoard", key = "{#result.id}")
            },
            evict = {
                    @CacheEvict(value = "userBoardByUser", key = "{#root.methodName, #principal.name}"),
                    @CacheEvict(value = "AllUserBoard")
            }
    )
    public Board updateBoard(Board boardDto, Principal principal) {

        return boardRepository.save(boardDto);
    }

    @Override
    public Boolean existsBoardById(Long id) {
        return boardRepository.existsBoardById(id);
    }
}
