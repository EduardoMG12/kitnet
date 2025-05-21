package com.kitnet.kitnet.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class UserTest {

    private static Validator validator;

    private User user;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test.user@example.com");
        user.setPhone("11987654321");
        user.setPassword("SecurePass123!");
        user.setAcceptTerms(true);
        user.setCpf("12345678900");
        user.setDocumentImageWithUser(new byte[]{1, 2, 3});
    }

    @Test
    void testUserCreationAndGetters() {
        assertThat(user.getFirstName()).isEqualTo("Test");
        assertThat(user.getLastName()).isEqualTo("User");
        assertThat(user.getEmail()).isEqualTo("test.user@example.com");
        assertThat(user.getPhone()).isEqualTo("11987654321");
        assertThat(user.getPassword()).isEqualTo("SecurePass123!");
        assertThat(user.getAcceptTerms()).isTrue();
        assertThat(user.getCpf()).isEqualTo("12345678900");
        assertArrayEquals(new byte[]{1, 2, 3}, user.getDocumentImageWithUser());
        assertThat(user.getId()).isNull();
    }

    @Test
    void testUserWithAllValidFields() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    void testUserWithNullFirstName() {
        user.setFirstName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("firstName");
    }

    @Test
    void testUserWithEmptyFirstName() {
        user.setFirstName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("firstName");
    }

    @Test
    void testUserWithNullLastName() {
        user.setLastName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("lastName");
    }

    @Test
    void testUserWithEmptyLastName() {
        user.setLastName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("lastName");
    }

    @Test
    void testUserWithNullEmail() {
        user.setEmail(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
    }

    @Test
    void testUserWithEmptyEmail() {
        user.setEmail("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // MUDANÇA AQUI: Espera 1 violação, pois @Email não falha para string vazia
        assertThat(violations).hasSize(1); // Ajustado para 1
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O email não pode estar em branco");
    }

    @Test
    void testUserWithInvalidEmailFormat() {
        user.setEmail("invalid-email");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Formato de email inválido");
    }


    @Test
    void testUserWithNullPassword() {
        user.setPassword(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("password");
    }

    @Test
    void testUserWithEmptyPassword() {
        user.setPassword("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Espera 2 violações para senha vazia (@NotBlank e @Size)
        assertThat(violations).hasSize(2);
    }

    @Test
    void testUserWithNullAcceptTerms() {
        user.setAcceptTerms(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("acceptTerms");
    }

    @Test
    void testUserWithNullCpf() {
        user.setCpf(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("cpf");
    }

    @Test
    void testUserWithEmptyCpf() {
        user.setCpf("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Espera 2 violações para CPF vazio (@NotBlank e @Pattern)
        assertThat(violations).hasSize(2);
    }

    @Test
    void testUserWithInvalidCpfFormat() {
        user.setCpf("123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("cpf");
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O CPF deve conter 11 dígitos numéricos");
    }


    @Test
    void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("A"); user1.setLastName("B"); user1.setEmail("a@example.com"); user1.setPhone("1"); user1.setPassword("p"); user1.setAcceptTerms(true); user1.setCpf("11111111111"); user1.setDocumentImageWithUser(new byte[]{});

        User user2 = new User();
        user2.setId(1L);
        user2.setFirstName("A"); user2.setLastName("B"); user2.setEmail("a@example.com"); user2.setPhone("1"); user2.setPassword("p"); user2.setAcceptTerms(true); user2.setCpf("11111111111"); user2.setDocumentImageWithUser(new byte[]{});

        User user3 = new User();
        user3.setId(2L);
        user3.setFirstName("C"); user3.setLastName("D"); user3.setEmail("c@example.com"); user3.setPhone("2"); user3.setPassword("q"); user3.setAcceptTerms(false); user3.setCpf("22222222222"); user3.setDocumentImageWithUser(new byte[]{});

        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());

        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1.hashCode()).isNotEqualTo(user3.hashCode());

        assertThat(user1).isEqualTo(user1);
        assertThat(user1).isNotEqualTo(null);
        assertThat(user1).isNotEqualTo(new Object());
    }
}
