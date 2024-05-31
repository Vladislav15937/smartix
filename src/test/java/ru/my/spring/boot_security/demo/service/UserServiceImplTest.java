package ru.my.spring.boot_security.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.repositoryes.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenSaveUser_thenPasswordEncodedAndBalanceSet() {
        User user = new User();
        user.setPassword("plainPassword");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        userService.save(user);

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(1000.0, savedUser.getBalance());
    }

    @Test
    public void whenUpdateById_thenUserUpdated() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setPassword("oldPassword");
        existingUser.setBalance(500.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        User updatedUser = new User();
        updatedUser.setPassword("newPassword");
        updatedUser.setBalance(2000.0);

        userService.updateById(1L, updatedUser);

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("encodedNewPassword", savedUser.getPassword());
        assertEquals(2000.0, savedUser.getBalance());
    }

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
}