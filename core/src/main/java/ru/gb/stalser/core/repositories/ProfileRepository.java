package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
