package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.core.entity.Sprint;
import ru.gb.stalser.core.entity.Tag;
import ru.gb.stalser.core.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {



    List<Task> findAllByTagsContaining (Tag tag);

}
