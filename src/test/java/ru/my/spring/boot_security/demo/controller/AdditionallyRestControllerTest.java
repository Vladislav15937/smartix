package ru.my.spring.boot_security.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.my.spring.boot_security.demo.dto.AdditionallyUserDto;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;
import ru.my.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdditionallyRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdditionallyRestController additionallyRestController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(additionallyRestController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "user")
    public void whenAddAdditionally_thenReturnsSuccess() throws Exception {
        AdditionallyUserDto additionallyUserDto = new AdditionallyUserDto();
        doNothing().when(userService).addAdditionallyUser(additionallyUserDto, null);
        mockMvc.perform(post("/additionally")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(additionallyUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Данные успешно добавлены!"));
    }

    @Test
    @WithMockUser(username = "user")
    public void whenUpdateAdditionally_thenReturnsUpdated() throws Exception {
        AdditionallyUserDto additionallyUserDto = new AdditionallyUserDto();
        doNothing().when(userService).updateAdditionallyUser(additionallyUserDto, null);
        mockMvc.perform(post("/additionally/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(additionallyUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Данные обновлены!"));
    }
}