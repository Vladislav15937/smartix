package ru.my.spring.boot_security.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdditionallyRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdditionallyRestController additionallyRestController;

    @Test
    public void whenAddAdditionally_thenRespondSuccess() {
        // Подготовка
        String username = "testUser";
        String password = "testPassword";
        String patronymic = "testPatronymic";
        String surname = "testSurname";
        String name = "testName";
        AdditionallyUser additionallyUser = new AdditionallyUser(patronymic, surname, name);
        User user = new User(username, password);
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        when(userService.findByUsername(username)).thenReturn(Optional.of(user));
        doNothing().when(userService).addAdditionallyUser(any(AdditionallyUser.class), any(User.class));

        ResponseEntity<String> response = additionallyRestController.addAdditionally(additionallyUser, principal);

        assertNotNull(response);
        assertEquals("данные успешно добавлены", response.getBody());
    }

    @Test
    public void whenUpdateAdditionally_thenRespondUpdated() {
        String username = "testUser";
        String password = "testPassword";
        String patronymic = "testPatronymic";
        String surname = "testSurname";
        String name = "testName";
        AdditionallyUser additionallyUser = new AdditionallyUser(patronymic, surname, name);
        User user = new User(username, password);
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        when(userService.findByUsername(username)).thenReturn(Optional.of(user));
        doNothing().when(userService).updateAdditionallyUser(any(AdditionallyUser.class), any(User.class));

        ResponseEntity<String> response = additionallyRestController.updateAdditionally(additionallyUser, principal);

        assertNotNull(response);
        assertEquals("Данные обновлены", response.getBody());
    }
}
