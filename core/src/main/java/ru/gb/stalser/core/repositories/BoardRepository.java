package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.User;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
Boolean existsBoardById(Long id);
List<Board> findAllByUsersContaining(User user);

}
