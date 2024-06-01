package ru.my.spring.boot_security.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.mapper.BalanceDtoMapper;
import ru.my.spring.boot_security.demo.repositoryes.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BalanceDtoMapper balanceDtoMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void registryUser_NewUser() {
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("password");
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(balanceDtoMapper.apply(any(User.class))).thenReturn(new BalanceDto());
        BalanceDto result = userService.registryUser(newUser);
        verify(userRepository).findByUsername("newUser");
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
        assertNotNull(result);
    }

    @Test
    public void updateAdditionallyUser_ShouldUpdateInfo() {
        User user = new User();
        user.setUsername("username");
        AdditionallyUser existingInfo = new AdditionallyUser();
        user.setAdditionallyUser(existingInfo);
        AdditionallyUser newInfo = new AdditionallyUser();
        newInfo.setEmail("newEmail");
        newInfo.setName("newName");
        userService.updateAdditionallyUser(newInfo, user);
        assertEquals("newEmail", user.getAdditionallyUser().getEmail());
        assertEquals("newName", user.getAdditionallyUser().getName());
        verify(userRepository).save(user);
    }

    @Test
    public void loadUserByUsername_UserDoesNotExist_ShouldThrowException() {
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }

    @Test
    public void findUserById_UserDoesNotExist_ShouldThrowException() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
    }

}