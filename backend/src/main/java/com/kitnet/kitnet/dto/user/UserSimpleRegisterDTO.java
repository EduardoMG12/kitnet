package com.kitnet.kitnet.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSimpleRegisterDTO {

    @NotBlank(message = "O nome/razão social não pode estar em branco")
    @Size(max = 200, message = "O nome/razão social deve ter no máximo 200 caracteres")
    private String name;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "A confirmação da senha não pode estar em branco")
    private String confirmPassword;

    @NotNull(message = "A aceitação dos termos de uso é obrigatória")
    private Boolean acceptTerms;

}