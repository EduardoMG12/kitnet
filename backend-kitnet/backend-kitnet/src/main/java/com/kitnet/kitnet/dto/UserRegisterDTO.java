package com.kitnet.kitnet.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    private String phone;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotNull
    private Boolean acceptTerms;

    @NotBlank
    private String cpf;

    private byte[] documentImageWithUser;
}
