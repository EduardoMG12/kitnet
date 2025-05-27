package com.kitnet.kitnet.dto.user;

import com.kitnet.kitnet.model.LegalPersonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserProfileUpdateDTO {

    @NotBlank(message = "O documento legal (CPF/CNPJ) não pode estar em branco")
    @Pattern(regexp = "^\\d{11}$|^\\d{14}$", message = "O documento legal deve conter 11 (CPF) ou 14 (CNPJ) dígitos numéricos")
    private String legalDocument;

    @NotNull(message = "O tipo de pessoa (Física/Jurídica) é obrigatório")
    private LegalPersonType legalPersonType;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String phone;

    @Size(max = 500, message = "A URL da foto de perfil deve ter no máximo 500 caracteres")
    private String profilePictureUrl;

}