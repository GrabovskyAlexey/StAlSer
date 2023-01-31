package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Invite;

public interface InviteRepository extends JpaRepository<Invite, Long> {
}
