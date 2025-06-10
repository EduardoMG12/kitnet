package com.kitnet.kitnet.dto.passwordReset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetConfirmDTO {
    private String token;

    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 8, max = 255, message = "A nova senha deve ter entre 8 e 255 caracteres.")
    private String newPassword;

    @NotBlank(message = "A confirmação da nova senha é obrigatória.")
    @Size(min = 8, max = 255, message = "A confirmação da nova senha deve ter entre 8 e 255 caracteres.")
    private String confirmNewPassword;
}