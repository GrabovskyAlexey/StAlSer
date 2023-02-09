package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.stalser.api.dto.invite.InviteStatus;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    @Value("${stalser.invite.url}")
    private String url;
    @Value("${stalser.invite.email-address-from}")
    private String emailFrom;
    private final InviteRepository inviteRepository;
    private final UserService userService;
    private final BoardService boardService;

    private final KafkaTemplate<String, SimpleTextEmailMessage> kafkaTemplate;

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
        SimpleTextEmailMessage message;
        invite.setStatus(InviteStatus.SENT);//устанавливаем статус, что приглашение отправлено
        invite.setInviteCode(UUID.randomUUID().toString());//генерируем уникальное число и сохраняем его в приглашении
        invite.setExpirationDate(Instant.now().plus(7, ChronoUnit.DAYS));//устанавливаем дату когда приглашение "протухнет"
        if (!boardService.existsBoardById(invite.getBoard().getId())) {
            //TODO Когда появится глобальный обработчик добавить туда исключение
            throw new InviteWithoutBoardException("Не удалось создать приглашение. Доска с id = " + invite.getBoard().getId() + " не найдена.");
        }
        String boardName = boardService.findById(invite.getBoard().getId()).getBoardName();
        message = configureMessage(invite.getEmail(), boardName);
        if (userService.existsByEmail(invite.getEmail())) {
            user = userService.findByEmail(invite.getEmail());
            message.setText(url + "/invites?code=" + invite.getInviteCode());//если пользователь существует, кидаем его на страницу с приглашением, где он может принять или отклонить его.
        } else {
            message.setText(url + "/register?code=" + invite.getInviteCode() + "&email=" + invite.getEmail().replaceAll("@", "%40"));//пользователя нет, кидаем на страницу регистрации
        }
        kafkaTemplate.send("simple-text-email", message);
        invite.setUser(user);
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

    private SimpleTextEmailMessage configureMessage(String email, String boardName) {
        return SimpleTextEmailMessage.builder()
                .from(emailFrom)
                .to(email)
                .subject("Приглашение на добавление на доску " + boardName)
                .build();
    }

    @Transactional
    public Boolean acceptInvite(String code) {
        Invite invite = inviteRepository.findByInviteCode(code).orElseThrow(() -> new EntityNotFoundException("Invite with code = " + code + " not found"));
        User user = invite.getUser();
        Board board = invite.getBoard();
        board.getUsers().add(user);
        boardService.updateBoard(board);
        invite.setStatus(InviteStatus.ACCEPT);
        inviteRepository.save(invite);
        return true;
    }
}
