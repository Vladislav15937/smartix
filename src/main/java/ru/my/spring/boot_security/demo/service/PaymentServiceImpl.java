package ru.my.spring.boot_security.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.repositoryes.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Page<Payments> getPagePayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
}
