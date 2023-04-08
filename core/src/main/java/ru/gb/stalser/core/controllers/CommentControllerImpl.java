package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.comment.CommentDto;
import ru.gb.stalser.api.dto.comment.CommentListResponse;
import ru.gb.stalser.core.controllers.interfaces.CommentController;
import ru.gb.stalser.core.mappers.CommentMapper;
import ru.gb.stalser.core.services.interfaces.CommentService;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/comments")
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Override
    public CommentListResponse getAllComments(Principal principal) {
        return CommentListResponse.builder()
                .comments(commentService.findAll().stream()
                        .map(commentMapper::mapToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CommentDto getCommentById(Long id, Principal principal) {
        return commentMapper.mapToDto(commentService.findById(id));
    }

    @Override
    public CommentDto addComment(CommentDto comment, Principal principal) {
        return commentMapper.mapToDto(commentService.save(commentMapper.mapFromDto(comment)));
    }

    @Override
    public void updateComment(Long id, CommentDto comment, Principal principal) {
        commentService.update(commentMapper.mapFromDto(comment));
    }


}
