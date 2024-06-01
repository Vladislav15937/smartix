package ru.my.spring.boot_security.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.repositoryes.PaymentRepository;

import java.security.Principal;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final UserService userService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserService userService) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
    }

    @Override
    public Page<Payments> getPagePaymentsByUserId(Principal principal, Pageable pageable) {
        User user = userService.findByUsername(principal.getName()).get();
        Long userId = user.getId();
        return paymentRepository.findAllByUserId(userId, pageable);
    }
}
