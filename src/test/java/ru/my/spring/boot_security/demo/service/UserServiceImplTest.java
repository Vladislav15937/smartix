package ru.my.spring.boot_security.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.my.spring.boot_security.demo.dto.AdditionallyUserDto;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.dto.RegistryDto;
import ru.my.spring.boot_security.demo.dto.mapper.BalanceDtoMapper;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.repositoryes.UserRepository;
import ru.my.spring.boot_security.demo.service.user.impl.UserServiceImpl;

import java.security.Principal;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
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
        userService = new UserServiceImpl(userRepository, passwordEncoder, balanceDtoMapper);
    }

    @Test
    void whenRegistryUser_thenReturnsBalanceDto() {
        RegistryDto registryDto = new RegistryDto();
        registryDto.setLogin("username");
        registryDto.setPassword("password");
        User user = new User();
        user.setUsername(registryDto.getLogin());
        user.setPassword(passwordEncoder.encode(registryDto.getPassword()));
        BalanceDto expectedBalanceDto = new BalanceDto();
        when(userRepository.findByUsername(registryDto.getLogin())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registryDto.getPassword())).thenReturn("encodedPassword");
        when(balanceDtoMapper.apply(any(User.class))).thenReturn(expectedBalanceDto);
        BalanceDto actualBalanceDto = userService.registryUser(registryDto);
        assertEquals(expectedBalanceDto, actualBalanceDto);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void whenAddAdditionallyUser_thenReturnsBoolean() {
        AdditionallyUserDto additionallyUserDto = new AdditionallyUserDto();
        Principal principal = mock(Principal.class);
        User user = new User();
        user.setAdditionallyUser(null);
        when(principal.getName()).thenReturn("username");
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByUsername("username")).thenReturn(optionalUser);
        boolean result = userService.addAdditionallyUser(additionallyUserDto, principal);
        assertTrue(optionalUser.isPresent());
        assertFalse(result);
        assertNotNull(user.getAdditionallyUser());
        verify(userRepository).findByUsername("username");
    }

    @Test
    void whenUpdateAdditionallyUser_thenUpdatesSuccessfully() {
        AdditionallyUserDto additionallyUserDto = new AdditionallyUserDto();
        Principal principal = mock(Principal.class);
        User user = new User();
        AdditionallyUser additionallyUser = new AdditionallyUser();
        user.setAdditionallyUser(additionallyUser);
        when(principal.getName()).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        userService.updateAdditionallyUser(additionallyUserDto, principal);
        assertEquals(additionallyUserDto.getEmail(), user.getAdditionallyUser().getEmail());
        assertEquals(additionallyUserDto.getName(), user.getAdditionallyUser().getName());
        verify(userRepository).save(user);
    }

    @Test
    void whenShowUserDetails_thenReturnsBalanceDto() {
        Principal principal = mock(Principal.class);
        User user = new User();
        BalanceDto expectedBalanceDto = new BalanceDto();
        when(principal.getName()).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(balanceDtoMapper.apply(user)).thenReturn(expectedBalanceDto);
        BalanceDto actualBalanceDto = userService.showUserDetails(principal);
        assertEquals(expectedBalanceDto, actualBalanceDto);
        verify(userRepository).findByUsername("username");
    }
}