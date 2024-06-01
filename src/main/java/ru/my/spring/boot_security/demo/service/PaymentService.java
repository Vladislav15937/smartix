package ru.my.spring.boot_security.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.my.spring.boot_security.demo.entity.Payments;

import java.security.Principal;

public interface PaymentService {

    Page<Payments> getPagePaymentsByUserId(Principal principal, Pageable pageable);
}
