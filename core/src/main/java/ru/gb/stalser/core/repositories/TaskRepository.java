package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.stalser.core.entity.Sprint;
import ru.gb.stalser.core.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t " +
            "join t.tags tg where tg.tagName like %:tagName%")
    List<Task> findAllBy(String tagName);

}
