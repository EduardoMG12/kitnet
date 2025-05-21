package com.kitnet.kitnet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "O sobrenome não pode estar em branco")
    @Size(max = 100, message = "O sobrenome deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "Formato de email inválido")
    @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String phone;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, max = 255, message = "A senha deve ter entre 8 e 255 caracteres")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "A aceitação dos termos é obrigatória")
    @Column(nullable = false)
    private Boolean acceptTerms;

    @NotBlank(message = "O CPF não pode estar em branco")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
    @Column(nullable = false, unique = true)
    private String cpf;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] documentImageWithUser;


}