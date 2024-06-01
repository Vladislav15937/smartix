package ru.my.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.my.spring.boot_security.demo.entity.Role;
import ru.my.spring.boot_security.demo.repositoryes.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
