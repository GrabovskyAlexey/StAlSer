package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Page<InviteDto>> getAllInvites(int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return ResponseEntity.ok(inviteService.findAll(pageIndex-1, 10).map(inviteMapper::mapToDto));
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
