package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Tag;
import ru.gb.stalser.core.repositories.TagRepository;
import ru.gb.stalser.core.services.interfaces.TagService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
       return tagRepository.findAll();
    }

    @Override
    public Tag findById(Long id) {
       return tagRepository.findById(id)
               .orElseThrow(()-> new EntityNotFoundException("Tag id = "  + id + " not found!"));
    }

    @Override
    public Tag save(Tag tag) {
     return tagRepository.save(tag);
    }

    @Override
    public void update(Tag tag) {
       tagRepository.save(tag);
    }

    @Override
    public void deleteById(Long id) {
       tagRepository.deleteById(id);
    }
}
