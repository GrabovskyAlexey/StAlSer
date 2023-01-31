package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.invite.InviteDto;
import ru.gb.stalser.core.controllers.interfaces.InviteController;
import ru.gb.stalser.core.mappers.InviteMapper;
import ru.gb.stalser.core.services.interfaces.InviteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/invites")
@RequiredArgsConstructor
public class InviteControllerImpl implements InviteController {
    private final InviteService inviteService;
    private final InviteMapper inviteMapper;
    @Override
    public ResponseEntity<List<InviteDto>> getAllInvites() {
        return ResponseEntity.ok(inviteService.findAll().stream().map(inviteMapper::mapToDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<InviteDto> getInviteById(Long id) {
        return ResponseEntity.ok(inviteMapper.mapToDto(inviteService.findById(id)));
    }

    @Override
    public ResponseEntity<InviteDto> addInvite(InviteDto invite) {
        return ResponseEntity.ok(inviteMapper.mapToDto(inviteService.save(inviteMapper.mapFromDto(invite))));
    }

    @Override
    public void updateInvite(Long id, InviteDto invite) {
        inviteService.update(inviteMapper.mapFromDto(invite));
    }

    @Override
    public void deleteInvite(Long id) {
        inviteService.deleteById(id);
    }
}
