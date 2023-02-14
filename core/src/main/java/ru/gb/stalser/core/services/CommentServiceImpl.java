package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Comment;
import ru.gb.stalser.core.repositories.CommentRepository;
import ru.gb.stalser.core.services.interfaces.CommentService;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Page<Comment> findAll(int pageIndex, int pageSize) {
        return commentRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Comment id - " + id + " not found"));
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }


}
