package ru.my.spring.boot_security.demo.service.payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.my.spring.boot_security.demo.dto.PaymentDto;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.utils.exception.InsufficientFundsException;

import java.security.Principal;

public interface PaymentService {

    Page<Payments> getPagePaymentsByUserId(Principal principal, Pageable pageable);

    void payByNumber(PaymentDto paymentDto, Principal principal) throws InsufficientFundsException;
}
