package ru.my.spring.boot_security.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.service.UserService;
import ru.my.spring.boot_security.demo.utils.InsufficientFundsException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistryRestControllerTest {

    private RegistryRestController registryRestController;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService() {
            @Override
            public void save(User user) {

            }

            @Override
            public List<User> show() {
                return null;
            }

            @Override
            public Optional<User> findByUsername(String username) {
                return Optional.empty();
            }

            @Override
            public void updateById(Long id, User user) {

            }

            @Override
            public void delete(Long id) {

            }

            @Override
            public Optional<User> getUserById(Long id) {
                return Optional.empty();
            }

            @Override
            public void registryUser(User user) {

            }

            @Override
            public void payByNumber(Map map) throws InsufficientFundsException {

            }

            @Override
            public void addAdditionallyUser(AdditionallyUser additionallyUser, User user) {

            }

            @Override
            public void updateAdditionallyUser(AdditionallyUser additionallyUser, User user) {

            }

            @Override
            public void historyPaymentUser() {

            }

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return null;
            }
        };

        registryRestController = new RegistryRestController(userService);
    }

    @Test
    public void whenAddUser_thenRespondOk() {
        User user = new User("89831752807", "vlad");
        ResponseEntity<HttpStatus> response = registryRestController.add(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
