package ru.my.spring.boot_security.demo.service;

import ru.my.spring.boot_security.demo.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void saveRole(Role role);
}
