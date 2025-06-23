package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.user.UserLoginDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegisterDTO registerDto;
    private UserLoginDTO loginDto;
    private User existingUser;
    private UUID existingUserId;

    @BeforeEach
    void setUp() {
        existingUserId = UUID.randomUUID();
        
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
        registerDto.setRoleName(RoleName.LESSEE);
        
        loginDto = new UserLoginDTO();
        loginDto.setEmail("existing.user@example.com");
        loginDto.setPassword("CorrectPassword123!");
        
        existingUser = new User();
        existingUser.setId(existingUserId);
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        existingUser.setEmail("existing.user@example.com");
        existingUser.setPassword("encodedCorrectPassword123!");
        existingUser.setAcceptTerms(true);
        existingUser.setCpf("00011122233");
        existingUser.setRoleName(RoleName.LESSOR);
    }


    @Test
    void testRegisterUserSuccess() throws Exception {
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword123!");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID());
            return user;
        });

        User registeredUser = userService.register(registerDto);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(registerDto.getEmail(), registeredUser.getEmail());
        assertEquals("encodedPassword123!", registeredUser.getPassword());

        verify(userRepository, times(1)).findByEmail(registerDto.getEmail());
        verify(passwordEncoder, times(1)).encode(registerDto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserPasswordMismatch() {
        registerDto.setConfirmPassword("WrongPassword!");
        
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.register(registerDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("As senhas não coincidem.", exception.getReason());

        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void testRegisterUserEmailAlreadyInUse() {
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(existingUser));
        
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.register(registerDto);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("E-mail já está em uso.", exception.getReason());

        verify(userRepository, times(1)).findByEmail(registerDto.getEmail());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }


    @Test
    void testLoginUserSuccess() throws Exception {
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(loginDto.getPassword(), existingUser.getPassword())).thenReturn(true);

        User loggedInUser = userService.login(loginDto);

        assertNotNull(loggedInUser);
        assertEquals(existingUser.getEmail(), loggedInUser.getEmail());
        assertEquals(existingUser.getFirstName(), loggedInUser.getFirstName());

        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
        verify(passwordEncoder, times(1)).matches(loginDto.getPassword(), existingUser.getPassword());
    }

    @Test
    void testLoginUserNotFound() {
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());
        
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.login(loginDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuário não encontrado.", exception.getReason());

        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testLoginUserIncorrectPassword() {
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(loginDto.getPassword(), existingUser.getPassword())).thenReturn(false);
        
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.login(loginDto);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Senha incorreta.", exception.getReason());

        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
        verify(passwordEncoder, times(1)).matches(loginDto.getPassword(), existingUser.getPassword());
    }
}
