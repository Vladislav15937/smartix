package ru.my.spring.boot_security.demo.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.my.spring.boot_security.demo.dto.AdditionallyUserDto;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.dto.RegistryDto;
import ru.my.spring.boot_security.demo.entity.User;

import java.security.Principal;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByUsername(String username);

    BalanceDto registryUser(RegistryDto registryDto);

    boolean addAdditionallyUser(AdditionallyUserDto additionallyUser, Principal principal);

    void updateAdditionallyUser(AdditionallyUserDto additionallyUser, Principal principal);

    BalanceDto showUserDetails(Principal principal);

    void save(User user);
}
