package com.kitnet.kitnet.dto.emailVerification;

import lombok.Data;

@Data
public class EmailVerificationResponseDTO {
    private String message;
    private boolean verified;

    public EmailVerificationResponseDTO(String message, boolean verified) {
        this.message = message;
        this.verified = verified;
    }
}