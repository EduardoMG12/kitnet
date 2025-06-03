package com.kitnet.kitnet.dto.emailVerification;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailVerificationRequestDTO {
    @NotBlank(message = "O token de verificação é obrigatório")
    private String token;
}