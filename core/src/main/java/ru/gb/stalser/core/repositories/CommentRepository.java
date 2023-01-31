package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {


}

