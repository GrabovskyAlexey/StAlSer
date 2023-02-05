package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.Invite;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.repositories.InviteRepository;
import ru.gb.stalser.core.services.interfaces.BoardService;
import ru.gb.stalser.core.services.interfaces.InviteService;
import ru.gb.stalser.core.services.interfaces.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;
    private final UserService userService;
    private final BoardService boardService;

    @Override
    public List<Invite> findAll() {
        return inviteRepository.findAll();
    }

    @Override
    public Invite findById(Long id) {
        return inviteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invite id = " + id + " not found"));
    }

    @Override
    @Transactional
    public Invite save(Invite invite) {
        User user;
        Board board;
        if (userService.existsByEmail(invite.getEmail())) {
            user = userService.findByEmail(invite.getEmail());
        } else {
            user = null;
        }
        invite.setUser(user);
        try {
            board = boardService.findById(invite.getBoard().getId());
            board.getUsers().add(user);
            boardService.updateBoard(board);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        return inviteRepository.save(invite);
    }

    @Override
    public Invite update(Invite invite) {
        return inviteRepository.save(invite);
    }

    @Override
    public void deleteById(Long id) {
        inviteRepository.deleteById(id);
    }
}
