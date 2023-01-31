package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Invite;
import ru.gb.stalser.core.repositories.InviteRepository;
import ru.gb.stalser.core.services.interfaces.InviteService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;

    @Override
    public List<Invite> findAll() {
        return inviteRepository.findAll();
    }

    @Override
    public Invite findById(Long id) {
        return inviteRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Invite id = " + id + " not found"));
    }

    @Override
    public Invite save(Invite invite) {
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
