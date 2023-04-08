package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.invite.InviteDto;
import ru.gb.stalser.api.dto.invite.InviteListResponse;
import ru.gb.stalser.core.controllers.interfaces.InviteController;
import ru.gb.stalser.core.mappers.InviteMapper;
import ru.gb.stalser.core.services.interfaces.InviteService;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/invites")
@RequiredArgsConstructor
public class InviteControllerImpl implements InviteController {
    private final InviteService inviteService;
    private final InviteMapper inviteMapper;

    @Override
    public InviteListResponse getAllInvites(Principal principal) {
        return InviteListResponse.builder()
                .invites(
                        inviteService.findAll().stream()
                                .map(inviteMapper::mapToDto)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public InviteDto getInviteById(Long id, Principal principal) {
        return inviteMapper.mapToDto(inviteService.findById(id));
    }

    @Override
    public InviteDto addInvite(InviteDto invite, Principal principal) {
        return inviteMapper.mapToDto(inviteService.save(inviteMapper.mapFromDto(invite)));
    }

    @Override
    public void updateInvite(Long id, InviteDto invite, Principal principal) {
        inviteService.update(inviteMapper.mapFromDto(invite));
    }

    @Override
    public void deleteInvite(Long id, Principal principal) {
        inviteService.deleteById(id);
    }
}
