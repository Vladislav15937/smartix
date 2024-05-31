package ru.my.spring.boot_security.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.repositoryes.PaymentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetPagePayments_thenRepositoryCalled() {
        Pageable pageable = mock(Pageable.class);
        Page<Payments> expectedPage = mock(Page.class);

        when(paymentRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Payments> actualPage = paymentService.getPagePayments(pageable);

        verify(paymentRepository).findAll(pageable);
        assertEquals(expectedPage, actualPage, "The returned page should be the same as the mock page");
    }
}