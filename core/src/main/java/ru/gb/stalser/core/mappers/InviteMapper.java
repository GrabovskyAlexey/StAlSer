package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.invite.InviteDto;
import ru.gb.stalser.core.entity.Invite;

@Mapper(uses = {UserMapper.class})
public interface InviteMapper {
    InviteDto mapToDto(Invite invite);

    Invite mapFromDto(InviteDto inviteDto);
}
