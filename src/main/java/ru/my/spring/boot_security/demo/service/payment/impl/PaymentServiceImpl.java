package ru.my.spring.boot_security.demo.service.payment.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.my.spring.boot_security.demo.constant.Constant;
import ru.my.spring.boot_security.demo.dto.PaymentDto;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.repositoryes.PaymentRepository;
import ru.my.spring.boot_security.demo.service.payment.PaymentService;
import ru.my.spring.boot_security.demo.service.user.UserService;
import ru.my.spring.boot_security.demo.utils.exception.InsufficientFundsException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final String notExists = Constant.USER_NOT_EXISTS.toString();
    private final String insufficientFunds = Constant.INSUFFICIENT_FUNDS.toString();
    private final PaymentRepository paymentRepository;

    private final UserService userService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserService userService) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
    }

    @Override
    public Page<Payments> getPagePaymentsByUserId(Principal principal, Pageable pageable) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(notExists));
        Long userId = user.getId();
        return paymentRepository.findAllByUserId(userId, pageable);
    }

    @Override
    @Transactional
    public void payByNumber(PaymentDto paymentDto, Principal principal) throws InsufficientFundsException {
        LocalDateTime localDateTime = LocalDateTime.now();
        User payer = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(notExists));
        User recipient = userService.findByUsername(paymentDto.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException(notExists));
        validateFunds(payer, paymentDto.getRequiredQuantity());
        processPayment(payer, recipient, paymentDto.getRequiredQuantity(), localDateTime);
        userService.save(payer);
        userService.save(recipient);
    }

    private void validateFunds(User payer, Double requiredQuantity) throws InsufficientFundsException {
        if (payer.getBalance() < requiredQuantity) {
            throw new InsufficientFundsException(insufficientFunds);
        }
    }

    private void processPayment(User payer, User recipient, Double amount, LocalDateTime dateTime) {
        payer.setBalance(payer.getBalance() - amount);
        Payments payment = createPayment(payer, recipient.getUsername(), amount, dateTime);
        payer.getPayments().add(payment);
        updateRecipientBalance(recipient, amount);
    }

    private Payments createPayment(User payer, String recipientLogin, Double amount, LocalDateTime dateTime) {
        Payments payment = new Payments();
        payment.setUser(payer);
        payment.setNumber(recipientLogin);
        payment.setSum(amount);
        payment.setDate(dateTime);
        return payment;
    }

    private void updateRecipientBalance(User recipient, Double amount) {
        recipient.setMobileBalance(Optional.ofNullable(recipient.getMobileBalance())
                .orElse(0.0) + amount);
        userService.save(recipient);
    }
}
