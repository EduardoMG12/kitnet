package com.kitnet.kitnet.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UserRegisterDTO createValidUserRegisterDTO() {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john.doe@example.com");
        dto.setPhone("11987654321");
        dto.setPassword("StrongPass123");
        dto.setConfirmPassword("StrongPass123");
        dto.setAcceptTerms(true);
        dto.setCpf("12345678900");
        dto.setDocumentImageWithUser(new byte[]{1, 2, 3});
        return dto;
    }

    @Test
    void testValidUserRegisterDTO() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deveria haver violações para um DTO válido");
    }

    @Test
    void testBlankFirstName() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setFirstName("");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullFirstName() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setFirstName(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testBlankLastName() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setLastName("");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullLastName() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setLastName(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidEmailFormat() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setEmail("invalid-email");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must be a well-formed email address", violations.iterator().next().getMessage());
    }

    @Test
    void testBlankEmail() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setEmail("");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullEmail() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setEmail(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testPasswordTooShort() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setPassword("short");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("size must be between 6 and"));
    }

    @Test
    void testBlankPassword() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setPassword("");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullPassword() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setPassword(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testBlankConfirmPassword() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setConfirmPassword("");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullConfirmPassword() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setConfirmPassword(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullAcceptTerms() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setAcceptTerms(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testBlankCpf() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setCpf("");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullCpf() {
        UserRegisterDTO dto = createValidUserRegisterDTO();
        dto.setCpf(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testGettersAndSetters() {
        UserRegisterDTO dto = new UserRegisterDTO();
        String firstName = "Jane";
        String lastName = "Smith";
        String email = "jane.smith@example.com";
        String password = "AnotherStrongPass";
        String cpf = "00987654321";

        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setConfirmPassword(password);
        dto.setAcceptTerms(true);
        dto.setCpf(cpf);

        assertEquals(firstName, dto.getFirstName());
        assertEquals(lastName, dto.getLastName());
        assertEquals(email, dto.getEmail());
        assertEquals(password, dto.getPassword());
        assertEquals(cpf, dto.getCpf());
        assertTrue(dto.getAcceptTerms());
    }
}
