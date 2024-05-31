package ru.my.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.utils.InsufficientFundsException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    public void save(User user);

    public List<User> show();

    public Optional<User> findByUsername(String username);

    public void updateById(Long id, User user);

    public void delete(Long id);

    public Optional<User> getUserById(Long id);

    public void registryUser(User user);

    public void payByNumber(Map map) throws InsufficientFundsException;

    public void addAdditionallyUser(AdditionallyUser additionallyUser, User user);

    public void updateAdditionallyUser(AdditionallyUser additionallyUser, User user);

    public void historyPaymentUser();
}
