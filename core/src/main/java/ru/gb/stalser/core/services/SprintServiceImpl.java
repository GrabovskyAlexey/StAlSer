package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Sprint;
import ru.gb.stalser.core.repositories.SprintRepository;
import ru.gb.stalser.core.services.interfaces.SprintService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor

public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;

    @Override
    public List<Sprint> findAll (){
        return sprintRepository.findAll();
    }

    @Override
    public Sprint findById (Long id){
        return sprintRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Sprint id = " + id + " not found"));
    }

    @Override
    public Sprint save (Sprint sprint){
        return sprintRepository.save(sprint);
    }

    @Override
    public Sprint update (Sprint sprint){
        return sprintRepository.save(sprint);
    }

    @Override
    public void deleteById (Long id){
        sprintRepository.deleteById(id);
    }

}
