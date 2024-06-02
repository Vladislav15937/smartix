package ru.my.spring.boot_security.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.dto.RegistryDto;
import ru.my.spring.boot_security.demo.service.user.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegistryRestControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistryRestController registryRestController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registryRestController).build();
    }

    @Test
    void whenAdd_thenReturnsBalanceDto() throws Exception {
        RegistryDto registryDto = new RegistryDto();
        BalanceDto balanceDto = new BalanceDto();
        when(userService.registryUser(any(RegistryDto.class))).thenReturn(balanceDto);
        mockMvc.perform(post("/registry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registryDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(balanceDto)));
        verify(userService).registryUser(any(RegistryDto.class));
    }
}