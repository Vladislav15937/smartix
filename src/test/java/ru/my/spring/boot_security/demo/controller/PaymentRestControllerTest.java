package ru.my.spring.boot_security.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.my.spring.boot_security.demo.dto.PaymentDto;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.service.payment.PaymentService;

import java.security.Principal;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentRestController paymentRestController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(paymentRestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @WithMockUser(username = "user")
    void whenPayByNumber_thenReturnsSuccess() throws Exception {
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("username");
        PaymentDto paymentDto = new PaymentDto("+1234567890", 100.0);
        doNothing().when(paymentService).payByNumber(eq(paymentDto), eq(mockPrincipal));
        mockMvc.perform(post("/payment")
                        .principal(mockPrincipal)
                        .contentType("application/json")
                        .content("{\"login\":\"+1234567890\",\"requiredQuantity\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Счёт мобильного телефона успешно пополнен!")));
    }

    @Test
    @WithMockUser(username = "user")
    void whenHistoryPayment_thenReturnsPageOfPayments() throws Exception {
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("username");
        Page<Payments> expectedPage = new PageImpl<>(Collections.emptyList());
        when(paymentService.getPagePaymentsByUserId(any(Principal.class), any(Pageable.class)))
                .thenReturn(expectedPage);
        mockMvc.perform(get("/payment/historyPayment")
                        .principal(mockPrincipal)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[]}"));
    }
}