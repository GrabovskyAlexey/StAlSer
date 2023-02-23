package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.restriction.RestrictionDto;
import ru.gb.stalser.core.entity.Restriction;

@Mapper
public interface RestrictionMapper {

    RestrictionDto mapToDto(Restriction restriction);

    Restriction mapFromDto (RestrictionDto restrictionDto);
}
