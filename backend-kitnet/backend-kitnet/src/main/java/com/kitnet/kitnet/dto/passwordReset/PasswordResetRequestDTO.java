package com.kitnet.kitnet.dto.passwordReset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequestDTO {
    @NotBlank(message = "O email é obrigatório para a recuperação de senha.")
    @Email(message = "Formato de email inválido.")
    private String email;
}