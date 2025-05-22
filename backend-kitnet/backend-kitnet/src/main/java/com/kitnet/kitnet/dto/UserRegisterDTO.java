package com.kitnet.kitnet.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    private String firstName;

    @NotBlank(message = "O sobrenome não pode estar em branco")
    private String lastName;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "O email não pode estar em branco")
    private String email;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String phone;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "A confirmação de senha não pode estar em branco")
    private String confirmPassword;

    @NotNull(message = "A aceitação dos termos é obrigatória")
    private Boolean acceptTerms;

    @NotBlank(message = "O CPF não pode estar em branco")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
    private String cpf;

    private byte[] documentImageWithUser;
}
