package com.kitnet.kitnet.service; // Mantenha o pacote do serviço, não da implementação

import com.kitnet.kitnet.dto.user.UserLoginDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.RoleName; // Importar UserType
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.impl.UserServiceImpl; // Importar a implementação real do serviço
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus; // Importar HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder
import org.springframework.web.server.ResponseStatusException; // Importar ResponseStatusException

import java.util.Optional;
import java.util.UUID; // Importar UUID

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock // Mock do repositório para simular interações com o banco de dados
    private UserRepository userRepository;

    @Mock // Mock do PasswordEncoder para simular a criptografia e comparação de senhas
    private PasswordEncoder passwordEncoder;

    @InjectMocks // Injeta os mocks (userRepository, passwordEncoder) nesta instância do serviço
    private UserServiceImpl userService; // Usar a implementação real do serviço

    private UserRegisterDTO registerDto;
    private UserLoginDTO loginDto;
    private User existingUser;
    private UUID existingUserId; // Para armazenar o UUID do usuário existente

    @BeforeEach
    void setUp() {
        // Geração de um UUID para o usuário existente
        existingUserId = UUID.randomUUID();

        // DTO de registro
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
        registerDto.setRoleName(RoleName.LESSEE); // Definir UserType

        // DTO de login
        loginDto = new UserLoginDTO();
        loginDto.setEmail("existing.user@example.com");
        loginDto.setPassword("CorrectPassword123!");

        // Usuário existente no banco de dados (simulado)
        existingUser = new User();
        existingUser.setId(existingUserId); // Definir UUID
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        existingUser.setEmail("existing.user@example.com");
        // A senha do usuário existente deve ser a senha "criptografada" para simular o banco
        existingUser.setPassword("encodedCorrectPassword123!");
        existingUser.setAcceptTerms(true);
        existingUser.setCpf("00011122233");
        existingUser.setRoleName(RoleName.LESSOR); // Definir UserType
    }


    @Test
    void testRegisterUserSuccess() throws Exception {
        // Simula que o email não está em uso
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
        // Simula a criptografia da senha
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword123!");
        // Simula o salvamento do usuário e atribui um ID UUID
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID()); // Atribui um UUID aleatório ao salvar
            return user;
        });

        User registeredUser = userService.register(registerDto);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId()); // Verifica se o ID UUID foi gerado
        assertEquals(registerDto.getEmail(), registeredUser.getEmail());
        assertEquals("encodedPassword123!", registeredUser.getPassword()); // Verifica se a senha foi codificada

        verify(userRepository, times(1)).findByEmail(registerDto.getEmail());
        verify(passwordEncoder, times(1)).encode(registerDto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserPasswordMismatch() {
        registerDto.setConfirmPassword("WrongPassword!"); // Senhas não coincidem

        // Espera que uma ResponseStatusException (BAD_REQUEST) seja lançada
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
        // Simula que o email já está em uso
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(existingUser));

        // Espera que uma ResponseStatusException (CONFLICT) seja lançada
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
        // Simula que o usuário é encontrado pelo email
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(existingUser));
        // Simula que a senha fornecida no login corresponde à senha "criptografada" do usuário existente
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
        // Simula que o usuário não é encontrado
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());

        // Espera que uma ResponseStatusException (NOT_FOUND) seja lançada
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.login(loginDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuário não encontrado.", exception.getReason());

        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString()); // Não deve chamar matches se o usuário não for encontrado
    }

    @Test
    void testLoginUserIncorrectPassword() {
        // Simula que o usuário é encontrado
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(existingUser));
        // Simula que a senha fornecida NÃO corresponde à senha "criptografada"
        when(passwordEncoder.matches(loginDto.getPassword(), existingUser.getPassword())).thenReturn(false);

        // Espera que uma ResponseStatusException (UNAUTHORIZED) seja lançada
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.login(loginDto);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Senha incorreta.", exception.getReason());

        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
        verify(passwordEncoder, times(1)).matches(loginDto.getPassword(), existingUser.getPassword());
    }
}
