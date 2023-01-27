package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
