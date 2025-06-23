package com.kitnet.kitnet.dto.user;

import com.kitnet.kitnet.model.enums.LegalPersonType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserProfileUpdateDTO {

    @Pattern(regexp = "^\\d{11}$|^\\d{14}$", message = "O documento legal deve conter 11 (CPF) ou 14 (CNPJ) dígitos numéricos")
    private String legalDocument;

    private LegalPersonType legalPersonType;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String phone;

    @Size(max = 500, message = "A URL da foto de perfil deve ter no máximo 500 caracteres")
    private String profilePictureUrl;

    @Size(max = 100, message = "A profissão deve ter no máximo 100 caracteres")
    private String profession;

    @Size(max = 200, message = "O nome do contato de emergência deve ter no máximo 200 caracteres")
    private String emergencyContactName;

    @Size(max = 20, message = "O telefone do contato de emergência deve ter no máximo 20 caracteres")
    private String emergencyContactPhone;

    private Double monthlyGrossIncome;

    private Boolean acceptTerms;
    private Boolean agreeToLgpdTerms;
    private Boolean acceptPrivacyPolicy;
    private Boolean authorizeCreditCheckAndCommunication;
    private Boolean acceptMarketingCommunications;

    private String rgFrontUrl;
    private String rgBackUrl;
    private String cpfUrl;
    private String cnhFrontUrl;
    private String cnhBackUrl;
    private String incomeProofUrl;
    private String residenceProofUrl;
    private String selfieWithDocumentUrl;

}
