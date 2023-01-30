package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.comment.CommentDto;
import ru.gb.stalser.core.controllers.interfaces.CommentController;
import ru.gb.stalser.core.mappers.CommentMapper;
import ru.gb.stalser.core.services.interfaces.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/comments")
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController{

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Override
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.findAll().stream()
                        .map(commentMapper::mapToDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<CommentDto> getCommentById(Long id) {
        return ResponseEntity.ok(commentMapper.mapToDto(commentService.findById(id)));
    }

    @Override
    public ResponseEntity<CommentDto> addComment(CommentDto comment) {
        return ResponseEntity.ok(commentMapper.mapToDto(commentService.save(commentMapper.mapFromDto(comment))));
    }

    @Override
    public void updateComment(Long id, CommentDto comment) {
        commentService.update(commentMapper.mapFromDto(comment));
    }



}
