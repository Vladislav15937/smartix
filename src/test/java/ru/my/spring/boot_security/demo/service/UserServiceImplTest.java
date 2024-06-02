package ru.my.spring.boot_security.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.my.spring.boot_security.demo.dto.AdditionallyUserDto;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.dto.RegistryDto;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.mapper.BalanceDtoMapper;
import ru.my.spring.boot_security.demo.repositoryes.UserRepository;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BalanceDtoMapper balanceDtoMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private RegistryDto registryDto;
    private AdditionallyUserDto additionallyUserDto;
    private Principal principal;
    private User user;

    @BeforeEach
    void setUp() {
        registryDto = new RegistryDto("ddd", "ddd");
        additionallyUserDto = new AdditionallyUserDto();
        principal = mock(Principal.class);
        user = new User();
    }

    @Test
    void registryUser_NewUser_ReturnsBalanceDto() {
        when(userRepository.findByUsername(registryDto.getLogin())).thenReturn(Optional.empty());
        BalanceDto result = userService.registryUser(registryDto);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registryUser_ExistingUser_ReturnsBalanceDto() {
        when(userRepository.findByUsername(registryDto.getLogin())).thenReturn(Optional.of(user));
        BalanceDto result = userService.registryUser(registryDto);
        verify(userRepository, never()).save(any(User.class));
    }
}