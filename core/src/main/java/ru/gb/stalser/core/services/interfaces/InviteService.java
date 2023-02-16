package ru.gb.stalser.core.services.interfaces;

import org.springframework.data.domain.Page;
import ru.gb.stalser.core.entity.Invite;


public interface InviteService {
    Page<Invite> findAll(int pageIndex, int pageSize);

    Invite findById(Long id);

    Invite save(Invite invite);

    Invite update(Invite invite);

    void deleteById(Long id);
}
