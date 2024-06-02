package ru.my.spring.boot_security.demo.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.my.spring.boot_security.demo.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
