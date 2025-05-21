package com.kitnet.kitnet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import com.kitnet.kitnet.exception.GlobalExceptionHandler;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({
        UserControllerTest.TestMocksConfig.class,
        GlobalExceptionHandler.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    static class TestMocksConfig {
        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }

        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }
    }

    @AfterEach
    void tearDown() {
        reset(userService);
        reset(userRepository);
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        UserRegisterDTO requestDto = new UserRegisterDTO();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPhone("11987654321");
        requestDto.setPassword("Password123!");
        requestDto.setConfirmPassword("Password123!");
        requestDto.setAcceptTerms(true);
        requestDto.setCpf("12345678900");

        User registeredUser = new User();
        registeredUser.setId(1L);
        registeredUser.setFirstName("John");
        registeredUser.setLastName("Doe");
        registeredUser.setEmail("john.doe@example.com");
        registeredUser.setCpf("12345678900");

        when(userService.register(any(UserRegisterDTO.class))).thenReturn(registeredUser);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(userService, times(1)).register(any(UserRegisterDTO.class));
    }

    @Test
    void testLoginUserSuccess() throws Exception {
        UserLoginDTO requestDto = new UserLoginDTO();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("securepassword");

        User loggedInUser = new User();
        loggedInUser.setId(2L);
        loggedInUser.setEmail("test@example.com");
        loggedInUser.setFirstName("Test");
        loggedInUser.setLastName("User");

        when(userService.login(any(UserLoginDTO.class))).thenReturn(loggedInUser);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).login(any(UserLoginDTO.class));
    }

    @Test
    void testRegisterUserInvalidInput() throws Exception {
        UserRegisterDTO requestDto = new UserRegisterDTO();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setEmail("");
        requestDto.setPhone("11987654321");
        requestDto.setPassword("Password123!");
        requestDto.setConfirmPassword("Password123!");
        requestDto.setAcceptTerms(true);
        requestDto.setCpf("12345678900");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).register(any(UserRegisterDTO.class));
    }

    @Test
    void testLoginUserInvalidInput() throws Exception {
        UserLoginDTO requestDto = new UserLoginDTO();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).login(any(UserLoginDTO.class));
    }

    @Test
    void testRegisterUserThrowsException() throws Exception {
        UserRegisterDTO requestDto = new UserRegisterDTO();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPhone("11987654321");
        requestDto.setPassword("Password123!");
        requestDto.setConfirmPassword("Password123!");
        requestDto.setAcceptTerms(true);
        requestDto.setCpf("12345678900");

        when(userService.register(any(UserRegisterDTO.class))).thenThrow(new RuntimeException("Email already registered"));


        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void testLoginUserThrowsException() throws Exception {
        UserLoginDTO requestDto = new UserLoginDTO();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("wrongpassword");

        when(userService.login(any(UserLoginDTO.class))).thenThrow(new RuntimeException("Invalid credentials"));


        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized());
    }
}
