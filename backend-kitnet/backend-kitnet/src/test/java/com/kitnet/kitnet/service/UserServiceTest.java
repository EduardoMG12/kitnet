package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserRegisterDTO registerDto;
    private UserLoginDTO loginDto;
    private User existingUser;

    @BeforeEach
    void setUp() {
        registerDto = new UserRegisterDTO();
        registerDto.setFirstName("John");
        registerDto.setLastName("Doe");
        registerDto.setEmail("john.doe@example.com");
        registerDto.setPhone("11987654321");
        registerDto.setPassword("Password123!");
        registerDto.setConfirmPassword("Password123!");
        registerDto.setAcceptTerms(true);
        registerDto.setDocumentImageWithUser(new byte[]{});
        registerDto.setCpf("12345678900");

        loginDto = new UserLoginDTO();
        loginDto.setEmail("existing.user@example.com");
        loginDto.setPassword("CorrectPassword123!");

        existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        existingUser.setEmail("existing.user@example.com");
        existingUser.setPassword("CorrectPassword123!");
        existingUser.setAcceptTerms(true);
        existingUser.setCpf("00011122233");
    }


    @Test
    void testRegisterUserSuccess() throws Exception {
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        User registeredUser = userService.register(registerDto);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(registerDto.getEmail(), registeredUser.getEmail());

        verify(userRepository, times(1)).findByEmail(registerDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserPasswordMismatch() {
        registerDto.setConfirmPassword("WrongPassword!");

        Exception exception = assertThrows(Exception.class, () -> {
            userService.register(registerDto);
        });

        assertEquals("As senhas não coincidem.", exception.getMessage());

        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUserEmailAlreadyInUse() {
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(existingUser));

        Exception exception = assertThrows(Exception.class, () -> {
            userService.register(registerDto);
        });

        assertEquals("E-mail já está em uso.", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(registerDto.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void testLoginUserSuccess() throws Exception {
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(existingUser));

        User loggedInUser = userService.login(loginDto);

        assertNotNull(loggedInUser);
        assertEquals(existingUser.getEmail(), loggedInUser.getEmail());
        assertEquals(existingUser.getFirstName(), loggedInUser.getFirstName());

        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
    }

    @Test
    void testLoginUserNotFound() {
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            userService.login(loginDto);
        });

        assertEquals("Usuário não encontrado.", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
    }

    @Test
    void testLoginUserIncorrectPassword() {
        existingUser.setPassword("DifferentPassword!");
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(existingUser));

        Exception exception = assertThrows(Exception.class, () -> {
            userService.login(loginDto);
        });

        assertEquals("Senha incorreta.", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
    }
}
