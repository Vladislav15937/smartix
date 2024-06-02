package ru.my.spring.boot_security.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.dto.RegistryDto;
import ru.my.spring.boot_security.demo.service.user.UserService;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/registry")
@Validated
public class RegistryRestController {

    private final UserService userService;

    @Autowired
    public RegistryRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BalanceDto> add(@Valid @RequestBody RegistryDto registryDto) {
        BalanceDto balanceDto = userService.registryUser(registryDto);
        log.info("пользователь добавлен, или его данные отображены если он уже зарегистрирован");
        return new ResponseEntity<>(balanceDto, HttpStatus.OK);
    }
}
