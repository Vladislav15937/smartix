package ru.my.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/registry")
public class RegistryRestController {

    private final UserService userService;

    @Autowired
    public RegistryRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody User user) {
        userService.registryUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
