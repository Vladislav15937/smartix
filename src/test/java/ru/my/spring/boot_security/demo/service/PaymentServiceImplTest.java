package ru.my.spring.boot_security.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.repositoryes.PaymentRepository;

import java.security.Principal;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    public void whenGetPagePaymentsByUserId_thenSuccess() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("username");
        User user = new User("username", "password");
        user.setId(1L);
        Page<Payments> expectedPage = mock(Page.class);
        Pageable pageable = Pageable.ofSize(10);
        when(userService.findByUsername(anyString())).thenReturn(java.util.Optional.of(user));
        when(paymentRepository.findAllByUserId(anyLong(), any(Pageable.class))).thenReturn(expectedPage);
        Page<Payments> actualPage = paymentService.getPagePaymentsByUserId(principal, pageable);
        assertNotNull(actualPage);
        assertEquals(expectedPage, actualPage);
        verify(userService).findByUsername("username");
        verify(paymentRepository).findAllByUserId(1L, pageable);
    }
}