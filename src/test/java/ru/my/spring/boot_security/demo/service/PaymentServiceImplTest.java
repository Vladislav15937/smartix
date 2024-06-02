package ru.my.spring.boot_security.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.my.spring.boot_security.demo.dto.PaymentDto;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.repositoryes.PaymentRepository;
import ru.my.spring.boot_security.demo.utils.InsufficientFundsException;

import java.security.Principal;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentServiceImplTest {

    @Mock
    private UserService userService;

    @Test
    public void whenPayByNumberWithMockedData_thenProcessPayment() {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("payer");

        User mockPayer = new User();
        mockPayer.setId(1L);
        mockPayer.setUsername("payer");
        mockPayer.setBalance(1000.0);

        User mockRecipient = new User();
        mockRecipient.setId(2L);
        mockRecipient.setUsername("recipient");
        mockRecipient.setMobileBalance(500.0);

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setLogin("recipient");
        paymentDto.setRequiredQuantity(100.0);

        when(userService.findByUsername("payer")).thenReturn(Optional.of(mockPayer));
        when(userService.findByUsername("recipient")).thenReturn(Optional.of(mockRecipient));

        assertEquals(1000.0, mockPayer.getBalance());
        assertEquals(500.0, mockRecipient.getMobileBalance());
    }
}