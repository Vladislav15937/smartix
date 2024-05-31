package ru.my.spring.boot_security.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @Test
    public void givenValidUser_whenShowUserDetails_thenReturnUserData() {
        String username = "testUser";
        double balance = 100.0;
        User user = new User(username, balance);
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        when(userService.findByUsername(username)).thenReturn(Optional.of(user));

        ResponseEntity<Map<String, Double>> response = userRestController.showUserDetails(principal);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(balance, response.getBody().get(username));
    }
}