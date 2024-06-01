package ru.my.spring.boot_security.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.my.spring.boot_security.demo.entity.Payments;
import ru.my.spring.boot_security.demo.service.PaymentService;
import ru.my.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentRestControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PaymentRestController(userService, paymentService))
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
    }

    @Test
    public void whenPayByNumber_thenRespondWithSuccess() throws Exception {
        String requestBody = "{\"phoneNumber\":\"1234567890\",\"amount\":100.0}";
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString("Счёт мобильного телефона успешно пополнен!");
        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON_UTF8).characterEncoding("UTF-8")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(string));
    }

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;


    @Test
    @WithMockUser
    public void whenHistoryPayment_thenRespondWithPageOfPayments() throws Exception {
        Page<Payments> paymentsPage = new PageImpl<>(Collections.singletonList(new Payments()));
        given(paymentService.getPagePaymentsByUserId(any(Principal.class), any(PageRequest.class)))
                .willReturn(paymentsPage);
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "gtnz";
            }
        };
        mockMvc.perform(get("/payment/historyPayment")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());
        verify(paymentService, times(1)).getPagePaymentsByUserId(any(Principal.class),
                any(PageRequest.class));
    }
}
