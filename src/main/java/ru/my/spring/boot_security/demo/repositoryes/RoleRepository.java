package ru.my.spring.boot_security.demo.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.my.spring.boot_security.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
