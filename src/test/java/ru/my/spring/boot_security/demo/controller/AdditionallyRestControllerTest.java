package ru.my.spring.boot_security.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.my.spring.boot_security.demo.dto.AdditionallyUserDto;
import ru.my.spring.boot_security.demo.service.user.UserService;

import java.security.Principal;
import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdditionallyRestControllerTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdditionallyRestController additionallyRestController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(additionallyRestController).build();
    }

    @Test
    @WithMockUser(username = "user")
    void whenAddAdditionally_thenReturnsSuccess() throws Exception {
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("username");
        when(userService.addAdditionallyUser(any(AdditionallyUserDto.class), eq(mockPrincipal))).thenReturn(true);
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        AdditionallyUserDto dto = new AdditionallyUserDto("aaa", "aaa", "d", "a", true, date);
        mockMvc.perform(post("/additionally")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Данные успешно добавлены!"));
        verify(userService).addAdditionallyUser(any(AdditionallyUserDto.class), eq(mockPrincipal));
    }

    @Test
    @WithMockUser(username = "user")
    void whenUpdateAdditionally_thenReturnsUpdated() throws Exception {
        AdditionallyUserDto additionallyUserDto = new AdditionallyUserDto();
        doNothing().when(userService).updateAdditionallyUser(additionallyUserDto, null);
        mockMvc.perform(post("/additionally/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(additionallyUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Данные обновлены!"));
    }
}