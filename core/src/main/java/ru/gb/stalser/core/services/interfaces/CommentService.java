package ru.gb.stalser.core.services.interfaces;

import java.util.List;

import ru.gb.stalser.core.entity.Comment;

public interface CommentService {
        List<Comment> findAll();

        Comment findById(Long id);

        Comment save(Comment comment);

        Comment update(Comment comment);

        void deleteById(Long id);

}
