package ru.my.spring.boot_security.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.service.user.UserService;

import java.security.Principal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserRestController userRestController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    void whenShowUserDetails_thenReturnsBalanceDto() throws Exception {
        BalanceDto balanceDto = new BalanceDto();
        when(userService.showUserDetails(any(Principal.class))).thenReturn(balanceDto);
        mockMvc.perform(get("/user-data")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(balanceDto)));
        verify(userService).showUserDetails(any(Principal.class));
    }
}