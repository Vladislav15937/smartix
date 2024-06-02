package ru.my.spring.boot_security.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.my.spring.boot_security.demo.dto.PaymentDto;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.service.payment.PaymentService;
import ru.my.spring.boot_security.demo.utils.exception.InsufficientFundsException;

import java.security.Principal;

@Log4j2
@RestController
@RequestMapping("/payment")
public class PaymentRestController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentRestController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> payByNumber(@RequestBody PaymentDto paymentDto, Principal principal) {
        try {
            paymentService.payByNumber(paymentDto, principal);
            return ResponseEntity.ok("\"Счёт мобильного телефона успешно пополнен!\"");
        } catch (InsufficientFundsException e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("/historyPayment")
    public ResponseEntity<Page<Payments>> historyPayment(@PageableDefault(size = 10, sort = {"id"},
            direction =
                    Sort.Direction.DESC) Pageable pageable,
                                                         Principal principal) {
        Page<Payments> payments = paymentService.getPagePaymentsByUserId(principal, pageable);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
