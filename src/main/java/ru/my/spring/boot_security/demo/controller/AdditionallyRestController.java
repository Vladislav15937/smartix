package ru.my.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/additionally")
public class AdditionallyRestController {

    private final UserService userService;

    @Autowired
    public AdditionallyRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> addAdditionally(@RequestBody AdditionallyUser additionallyUser,
                                                  Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        userService.addAdditionallyUser(additionallyUser, user);
        return ResponseEntity.ok("данные успешно добавлены");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping
    @RequestMapping("/update")
    public ResponseEntity<String> updateAdditionally(@RequestBody AdditionallyUser additionallyUser,
                                                     Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        userService.updateAdditionallyUser(additionallyUser, user);
        return ResponseEntity.ok("Данные обновлены");
    }
}
