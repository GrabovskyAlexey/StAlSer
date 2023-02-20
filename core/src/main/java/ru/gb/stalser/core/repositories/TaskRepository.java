package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Tag;
import ru.gb.stalser.core.entity.Task;
import ru.gb.stalser.core.entity.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> getTasksByAssigneeContaining (User userId);
    List<Task> findAllByTagsContaining (Tag tag);

}
