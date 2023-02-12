package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.core.entity.Tag;

@Mapper
public interface TagMapper {

    TagDto mapToDto(Tag tag);

    Tag mapFromDto (TagDto tagDto);
}
