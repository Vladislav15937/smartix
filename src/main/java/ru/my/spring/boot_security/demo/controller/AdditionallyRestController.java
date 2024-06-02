package ru.my.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.my.spring.boot_security.demo.dto.AdditionallyUserDto;
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

    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> addAdditionally(@RequestBody AdditionallyUserDto additionallyUser,
                                                  Principal principal) {
        userService.addAdditionallyUser(additionallyUser, principal);
        return ResponseEntity.ok("Данные успешно добавлены!");
    }

    @PostMapping(path = "/update", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateAdditionally(@RequestBody AdditionallyUserDto additionallyUser,
                                                     Principal principal) {
        userService.updateAdditionallyUser(additionallyUser, principal);
        return ResponseEntity.ok("Данные обновлены!");
    }
}
