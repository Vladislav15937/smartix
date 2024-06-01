package ru.my.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.utils.InsufficientFundsException;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByUsername(String username);

    BalanceDto registryUser(User user);

    void payByNumber(Map map, Principal principal) throws InsufficientFundsException;

    void addAdditionallyUser(AdditionallyUser additionallyUser, User user);

    void updateAdditionallyUser(AdditionallyUser additionallyUser, User user);

}
