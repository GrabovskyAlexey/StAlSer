package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Role;
import ru.gb.stalser.core.repositories.RoleRepository;
import ru.gb.stalser.core.services.interfaces.RoleService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public Page<Role> findAll(int pageIndex, int pageSize) {
        return roleRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role id - " + id + " not found"));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<Role> findByName(String roleUser) {
        return roleRepository.findRoleByRoleName(roleUser);
    }

}