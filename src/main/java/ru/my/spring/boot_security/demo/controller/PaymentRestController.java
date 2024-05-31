package ru.my.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.service.PaymentService;
import ru.my.spring.boot_security.demo.service.UserService;
import ru.my.spring.boot_security.demo.utils.InsufficientFundsException;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentRestController {

    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public PaymentRestController(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<String> payByNumber(@RequestBody Map<String, Double> map) {
        try {
            userService.payByNumber(map);
        } catch (InsufficientFundsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Счёт мобильного телефона успешно пополнен!");
    }

    @GetMapping("/historyPayment")
    public ResponseEntity<Page<Payments>> historyPayment(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Payments> payments = paymentService.getPagePayments(pageable);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
