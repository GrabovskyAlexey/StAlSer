package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
Boolean existsBoardById(Long id);

}
