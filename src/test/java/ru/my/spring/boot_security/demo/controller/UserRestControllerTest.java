package ru.my.spring.boot_security.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.my.spring.boot_security.demo.entity.User;
import ru.my.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(userRestController).build();
    }

    @Test
    public void whenShowUserDetails_thenRespondWithMap() throws Exception {
        Principal mockPrincipal = mock(Principal.class);
        given(mockPrincipal.getName()).willReturn("testUser");
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setBalance(100.0);
        given(userService.findByUsername("testUser")).willReturn(Optional.of(testUser));
        Map<String, Double> userDetails = new HashMap<>();
        userDetails.put("testUser", 100.0);
        mockMvc.perform(get("/user-data")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userDetails)));
        verify(userService, times(1)).findByUsername("testUser");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}