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
    @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, max = 255, message = "A senha deve ter entre 8 e 255 caracteres")
    private String password;

    @NotBlank(message = "A confirmação de senha não pode estar em branco")
    private String confirmPassword;

    @NotNull(message = "A aceitação dos termos é obrigatória")
    private Boolean acceptTerms;

}