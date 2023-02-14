package ru.gb.stalser.core.services.interfaces;

import org.springframework.data.domain.Page;
import ru.gb.stalser.core.entity.Tag;
import ru.gb.stalser.core.entity.Task;


public interface TagService {

    Page<Tag> findAll(int pageIndex, int pageSize);

    Tag findById(Long id);

    Tag save(Tag tag);

    void update(Tag tag);

    void deleteById(Long id);

}
