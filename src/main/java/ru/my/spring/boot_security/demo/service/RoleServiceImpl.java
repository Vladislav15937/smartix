package ru.my.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.my.spring.boot_security.demo.entity.Role;
import ru.my.spring.boot_security.demo.repositoryes.RoleRepositry;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepositry roleRepositry;

    public RoleServiceImpl(RoleRepositry roleRepositry) {
        this.roleRepositry = roleRepositry;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepositry.findAll();
    }

    @Override
    public void saveRole(Role role) {
        roleRepositry.save(role);
    }
}
