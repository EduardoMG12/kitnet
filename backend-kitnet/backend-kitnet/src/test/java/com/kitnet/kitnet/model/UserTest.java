package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.RoleName;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll; // Importar AfterAll
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID; // Importar UUID

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class UserTest {

    private static ValidatorFactory validatorFactory; // Mudar para static para ser fechado no AfterAll
    private static Validator validator;

    private User user;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDownValidator() {
        if (validatorFactory != null) {
            validatorFactory.close();
        }
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
        user.setRoleName(RoleName.LESSEE); // Definir um UserType padrão para testes válidos
    }

    // Helper para criar um usuário válido com um ID UUID opcional
    private User createValidUser(UUID id) {
        User validUser = new User();
        validUser.setId(id);
        validUser.setFirstName("Valid");
        validUser.setLastName("User");
        validUser.setEmail("valid.user@example.com");
        validUser.setPhone("99988877766");
        validUser.setPassword("ValidPass123!");
        validUser.setAcceptTerms(true);
        validUser.setCpf("00011122233");
        validUser.setDocumentImageWithUser(new byte[]{});
        validUser.setRoleName(RoleName.LESSOR);
        return validUser;
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
        assertThat(user.getRoleName()).isEqualTo(RoleName.LESSEE);
        assertThat(user.getId()).isNull(); // ID é nulo antes de ser persistido
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
        // @NotBlank e @Email podem gerar 2 violações para string vazia, dependendo da ordem e implementação
        assertThat(violations).hasSize(1); // @NotBlank
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
    void testUserWithNullUserType() {
        user.setRoleName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("userType");
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O tipo de usuário é obrigatório");
    }

    @Test
    void testEqualsAndHashCode() {
        // Gera UUIDs aleatórios para os IDs
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID(); // Diferente de id1

        User user1 = createValidUser(id1);
        user1.setEmail("user1@example.com");
        user1.setCpf("11111111111");

        User user2 = createValidUser(id1); // Mesmo ID que user1
        user2.setEmail("user1@example.com"); // Mesmos outros dados para equals
        user2.setCpf("11111111111");

        User user3 = createValidUser(id2); // ID diferente
        user3.setEmail("user3@example.com");
        user3.setCpf("33333333333");

        // Objetos com os mesmos IDs e outros campos devem ser iguais
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());

        // Objetos com IDs diferentes não devem ser iguais
        assertThat(user1).isNotEqualTo(user3);
        // Hash codes podem colidir, mas geralmente não para objetos diferentes
        // assertThat(user1.hashCode()).isNotEqualTo(user3.hashCode()); // Esta asserção é mais frágil

        // Testes de borda para equals
        assertThat(user1).isEqualTo(user1); // Comparar com ele mesmo
        assertThat(user1).isNotEqualTo(null); // Comparar com nulo
        assertThat(user1).isNotEqualTo(new Object()); // Comparar com objeto de tipo diferente
    }

    @Test
    void testGetAuthorities() {
        user.setRoleName(RoleName.LESSEE);
        assertThat(user.getAuthorities()).hasSize(1);
        assertThat(user.getAuthorities()).extracting("authority").containsExactly("ROLE_LESSEE");

        user.setRoleName(RoleName.LESSOR);
        assertThat(user.getAuthorities()).hasSize(1);
        assertThat(user.getAuthorities()).extracting("authority").containsExactly("ROLE_LESSOR");
    }

    @Test
    void testGetUsername() {
        user.setEmail("test@email.com");
        assertThat(user.getUsername()).isEqualTo("test@email.com");
    }

    @Test
    void testGetPassword() {
        user.setPassword("mySecurePassword");
        assertThat(user.getPassword()).isEqualTo("mySecurePassword");
    }

    @Test
    void testIsAccountNonExpired() {
        assertThat(user.isAccountNonExpired()).isTrue();
    }

    @Test
    void testIsAccountNonLocked() {
        assertThat(user.isAccountNonLocked()).isTrue();
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertThat(user.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void testIsEnabled() {
        user.setAcceptTerms(true);
        assertThat(user.isEnabled()).isTrue();

        user.setAcceptTerms(false);
        assertThat(user.isEnabled()).isFalse();
    }
}
