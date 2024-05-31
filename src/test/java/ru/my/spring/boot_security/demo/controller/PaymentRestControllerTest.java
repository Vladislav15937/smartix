package ru.my.spring.boot_security.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.service.PaymentService;
import ru.my.spring.boot_security.demo.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentRestControllerTest {

    private PaymentRestController paymentRestController;
    private UserService userService;
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        paymentService = mock(PaymentService.class);
        paymentRestController = new PaymentRestController(userService, paymentService);
    }

    @Test
    public void whenPayByNumber_thenRespondSuccess() {

        Map<String, Double> paymentInfo = new HashMap<>();
        paymentInfo.put("phoneNumber", 12345.0);
        paymentInfo.put("amount", 150.0);

        ResponseEntity<String> response = paymentRestController.payByNumber(paymentInfo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Счёт мобильного телефона успешно пополнен!", response.getBody());
    }

    @Test
    public void whenHistoryPayment_thenReturnPaymentsPage() {
        Page<Payments> paymentsPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(paymentService.getPagePayments(pageable)).thenReturn(paymentsPage);

        ResponseEntity<Page<Payments>> response = paymentRestController.historyPayment(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentsPage, response.getBody());
    }
}
