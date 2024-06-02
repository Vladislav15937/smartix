package ru.my.spring.boot_security.demo.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.my.spring.boot_security.demo.dto.AdditionallyUserDto;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.dto.RegistryDto;
import ru.my.spring.boot_security.demo.dto.mapper.AdditionallyUserDtoMapper;
import ru.my.spring.boot_security.demo.dto.mapper.BalanceDtoMapper;
import ru.my.spring.boot_security.demo.dto.mapper.UserDtoMapper;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.repositoryes.UserRepository;
import ru.my.spring.boot_security.demo.service.user.UserService;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final BalanceDtoMapper balanceDtoMapper;

    @Autowired
    @Lazy
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           BalanceDtoMapper balanceDtoMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.balanceDtoMapper = balanceDtoMapper;
    }

    @Override
    @Transactional
    public BalanceDto registryUser(RegistryDto registryDto) {
        if (userRepository.findByUsername(registryDto.getLogin()).isEmpty()) {
            registryDto.setPassword(passwordEncoder.encode(registryDto.getPassword()));
            UserDtoMapper userDtoMapper = new UserDtoMapper();
            User user = userDtoMapper.apply(registryDto);
            userRepository.save(user);
            return balanceDtoMapper.apply(userDtoMapper.apply(registryDto));
        } else {
            return balanceDtoMapper.apply(userRepository.findByUsername(registryDto.getLogin()).orElseThrow());
        }
    }

    @Override
    @Transactional
    public boolean addAdditionallyUser(AdditionallyUserDto additionallyUser, Principal principal) {
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        User user = optionalUser.get();
        if (user.getAdditionallyUser() == null) {
            AdditionallyUserDtoMapper additionallyUserDtoMapper = new AdditionallyUserDtoMapper();
            AdditionallyUser additionallyUserEntity = additionallyUserDtoMapper.apply(additionallyUser);
            additionallyUserEntity.setUser(user);
            user.setAdditionallyUser(additionallyUserEntity);
            userRepository.save(user);
        }
        return false;
    }

    @Override
    @Transactional
    public void updateAdditionallyUser(AdditionallyUserDto additionallyUser, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));
        user.getAdditionallyUser().setEmail(additionallyUser.getEmail());
        user.getAdditionallyUser().setName(additionallyUser.getName());
        user.getAdditionallyUser().setGender(additionallyUser.getGender());
        user.getAdditionallyUser().setDateOfBirth(additionallyUser.getDateOfBirth());
        user.getAdditionallyUser().setPatronymic(additionallyUser.getPatronymic());
        user.getAdditionallyUser().setSurname(additionallyUser.getSurname());
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public BalanceDto showUserDetails(Principal principal) {
        return new BalanceDtoMapper().apply(userRepository
                .findByUsername(principal.getName()).orElseThrow());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
