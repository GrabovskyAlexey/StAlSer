package ru.gb.stalser.core.services.interfaces;

import ru.gb.stalser.core.entity.Invite;

import java.util.List;

public interface InviteService {
    List<Invite> findAll();

    Invite findById(Long id);

    Invite save(Invite invite);

    Invite update(Invite invite);

    void deleteById(Long id);
}
