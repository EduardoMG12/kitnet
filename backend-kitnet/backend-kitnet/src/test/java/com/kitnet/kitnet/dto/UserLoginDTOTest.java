package com.kitnet.kitnet.dto;

import org.junit.jupiter.api.AfterAll; // Importar AfterAll
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginDTOTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        if (validatorFactory != null) {
            validatorFactory.close();
        }
    }

    @Test
    void testValidUserLoginDTO() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("securepassword123");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deveria haver violações para um DTO válido");
    }

    @Test
    void testInvalidEmailFormat() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("invalid-email");
        dto.setPassword("securepassword123");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria haver violações para e-mail inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Deveria haver uma violação para o campo 'email'");
    }

    @Test
    void testBlankEmail() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("");
        dto.setPassword("securepassword123");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria haver violações para e-mail em branco");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Deveria haver uma violação para o campo 'email'");
    }

    @Test
    void testNullEmail() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail(null); // Nulo
        dto.setPassword("securepassword123");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria haver violações para e-mail nulo");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Deveria haver uma violação para o campo 'email'");
    }

    @Test
    void testBlankPassword() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria haver violações para senha em branco");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Deveria haver uma violação para o campo 'password'");
    }

    @Test
    void testNullPassword() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("test@example.com");
        dto.setPassword(null);

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria haver violações para senha nula");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Deveria haver uma violação para o campo 'password'");
    }

    @Test
    void testGettersAndSetters() {
        UserLoginDTO dto = new UserLoginDTO();
        String email = "another@example.com";
        String password = "anotherpassword";

        dto.setEmail(email);
        dto.setPassword(password);

        assertEquals(email, dto.getEmail());
        assertEquals(password, dto.getPassword());
    }
}
