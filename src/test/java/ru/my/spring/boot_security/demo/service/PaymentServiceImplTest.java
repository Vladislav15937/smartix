package ru.my.spring.boot_security.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.my.spring.boot_security.demo.dto.PaymentDto;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.repositoryes.PaymentRepository;
import ru.my.spring.boot_security.demo.service.payment.impl.PaymentServiceImpl;
import ru.my.spring.boot_security.demo.service.user.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private Principal principal;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void whenGetPagePaymentsByUserId_thenReturnsPage() {
        User user = new User();
        user.setId(1L);
        Long userId = user.getId();
        Page<Payments> expectedPage = Page.empty();
        when(principal.getName()).thenReturn("username");
        when(userService.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(paymentRepository.findAllByUserId(eq(userId), any(Pageable.class))).thenReturn(expectedPage);
        Page<Payments> actualPage = paymentService.getPagePaymentsByUserId(principal, Pageable.ofSize(1));
        assertEquals(expectedPage, actualPage);
        verify(userService).findByUsername(anyString());
        verify(paymentRepository).findAllByUserId(eq(userId), any(Pageable.class));
    }

    @Test
    void whenPayByNumber_thenProcessPayment() {
        User payer = new User();
        User recipient = new User();
        PaymentDto paymentDto = new PaymentDto();
        payer.setBalance(100.0);
        paymentDto.setRequiredQuantity(50.0);
        paymentDto.setLogin("recipientUsername");
        payer.setPayments(new ArrayList<>());
        when(principal.getName()).thenReturn("payerUsername");
        when(userService.findByUsername("payerUsername")).thenReturn(Optional.of(payer));
        when(userService.findByUsername("recipientUsername")).thenReturn(Optional.of(recipient));
        assertDoesNotThrow(() -> paymentService.payByNumber(paymentDto, principal));
        verify(userService, times(3)).save(any(User.class));
        assertEquals(50.0, payer.getBalance(), 0.01);
        verify(userService).findByUsername("payerUsername");
        verify(userService).findByUsername("recipientUsername");
    }

    @Test
    void whenPayByNumberWithInsufficientFunds_thenThrowException() {
        User payer = new User();
        PaymentDto paymentDto = new PaymentDto();
        payer.setBalance(20.0);
        paymentDto.setRequiredQuantity(50.0);
        when(principal.getName()).thenReturn("payerUsername");
        when(userService.findByUsername("payerUsername")).thenReturn(Optional.of(payer));
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            paymentService.payByNumber(paymentDto, principal);
        });
        verify(userService).findByUsername(anyString());
    }
}