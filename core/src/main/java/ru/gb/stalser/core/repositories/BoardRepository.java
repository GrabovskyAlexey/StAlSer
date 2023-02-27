package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.User;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Boolean existsBoardById(Long id);

//    List<Board> findAllByUsersContaining(User user);

    @Query(value = "select b from Board b join b.boardUserRoles br where br.user = :user")
    List<Board> findAllBoardWithUser(@Param("user") User user);

}
