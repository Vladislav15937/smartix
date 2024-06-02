package ru.my.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.service.user.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user-data")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<BalanceDto> showUserDetails(Principal principal) {
        return new ResponseEntity<>(userService.showUserDetails(principal), HttpStatus.OK);
    }
}
