package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.comment.CommentDto;
import ru.gb.stalser.core.entity.Comment;

@Mapper(uses = {UserMapper.class})
public interface CommentMapper {

    CommentDto mapToDto(Comment entity);

    Comment mapFromDto(CommentDto dto);
}