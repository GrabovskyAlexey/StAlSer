package ru.gb.stalser.core.services.interfaces;

import org.springframework.data.domain.Page;
import ru.gb.stalser.core.entity.Comment;

public interface CommentService {
        Page<Comment> findAll(int pageIndex, int pageSize);

        Comment findById(Long id);

        Comment save(Comment comment);

        Comment update(Comment comment);

        void deleteById(Long id);

}
