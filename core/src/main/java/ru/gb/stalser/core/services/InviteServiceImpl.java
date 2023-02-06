package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.stalser.api.dto.notify.SimpleTextEmailMessage;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.Invite;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.exceptions.InviteWithoutBoardException;
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
        User user = null;
        Board board;
        if (userService.existsByEmail(invite.getEmail())) {
            user = userService.findByEmail(invite.getEmail());
        }
        invite.setUser(user);
        try {
            board = boardService.findById(invite.getBoard().getId());
            board.getUsers().add(user);
            boardService.updateBoard(board);
            SimpleTextEmailMessage message = configureSimpleTextMsgFromInvite(invite);
        } catch (EntityNotFoundException e) {
            //TODO Когда появится глобальный обработчик добавить туда исключение
            throw new InviteWithoutBoardException("Не удалось создать приглашение. Доска с id = " + invite.getBoard().getId() + " не найдена.");
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

    private SimpleTextEmailMessage configureSimpleTextMsgFromInvite(Invite invite) {
        return SimpleTextEmailMessage.builder()
                .to(invite.getEmail())
                .from("no-replay@stalser.com")
                .subject("Приглашение на доску " + invite.getBoard().getBoardName())
                .text("Для принятия приглашения перейдите по ссылке ..........").build();
    }
}
