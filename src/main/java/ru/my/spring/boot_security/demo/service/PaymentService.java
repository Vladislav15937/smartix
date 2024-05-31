package ru.my.spring.boot_security.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.my.spring.boot_security.demo.entity.Payments;

public interface PaymentService {

    public Page<Payments> getPagePayments(Pageable pageable);
}
