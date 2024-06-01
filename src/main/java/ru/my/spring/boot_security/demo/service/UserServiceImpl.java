package ru.my.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.entity.Role;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.mapper.BalanceDtoMapper;
import ru.my.spring.boot_security.demo.repositoryes.UserRepository;
import ru.my.spring.boot_security.demo.utils.InsufficientFundsException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final BalanceDtoMapper balanceDtoMapper;

    @Autowired
    @Lazy
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, BalanceDtoMapper balanceDtoMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.balanceDtoMapper = balanceDtoMapper;
    }

    @Override
    @Transactional
    public BalanceDto registryUser(User user) {

        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setBalance(1000.0);
            userRepository.save(user);
            return balanceDtoMapper.apply(user);
        } else {
            return balanceDtoMapper.apply(userRepository.findByUsername(user.getUsername()).get());
        }
    }

    @Override
    @Transactional
    public void payByNumber(Map map, Principal principal) throws InsufficientFundsException {
        LocalDateTime localDateTime = LocalDateTime.now();
        String login = map.keySet().toArray()[0].toString();
        Double requiredQuantity = (Double) map.get(login);
        User user1 = userRepository.findByUsername(principal.getName()).get();
        User user2 = userRepository.findByUsername(login).get();

        if (user1.getBalance() >= requiredQuantity) {
            user1.setBalance(user1.getBalance() - requiredQuantity);
            Payments payments1 = new Payments();
            payments1.setUser(user1);
            payments1.setNumber(user2.getUsername());
            payments1.setSum(requiredQuantity);
            payments1.setDate(localDateTime);
            List<Payments> payments = user1.getPayments();
            payments.add(payments1);
            user1.setPayments(payments);
            if (user2.getMobileBalance() != null) {
                user2.setMobileBalance(user2.getMobileBalance() + requiredQuantity);
            } else {
                user2.setMobileBalance(0.0 + requiredQuantity);
            }
            userRepository.save(user2);
        } else {
            throw new InsufficientFundsException("На счёте недостаточно средств!!!");
        }
    }

    @Override
    @Transactional
    public void addAdditionallyUser(AdditionallyUser additionallyUser, User user) {
        if (userRepository.findByUsername(user.getUsername()).get().getAdditionallyUser() == null) {
            additionallyUser.setUser(userRepository.findByUsername(user.getUsername()).get());
            user.setAdditionallyUser(additionallyUser);
        }
    }

    @Override
    @Transactional
    public void updateAdditionallyUser(AdditionallyUser additionallyUser, User user) {
        user.getAdditionallyUser().setEmail(additionallyUser.getEmail());
        user.getAdditionallyUser().setName(additionallyUser.getName());
        user.getAdditionallyUser().setGender(additionallyUser.getGender());
        user.getAdditionallyUser().setDateOfBirth(additionallyUser.getDateOfBirth());
        user.getAdditionallyUser().setPatronymic(additionallyUser.getPatronymic());
        user.getAdditionallyUser().setSurname(additionallyUser.getSurname());
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("нет юзера");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
