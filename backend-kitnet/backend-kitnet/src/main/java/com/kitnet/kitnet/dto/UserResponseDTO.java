package com.kitnet.kitnet.dto;

import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String profilePictureUrl;
    private Set<RoleName> roles;
    private VerificationStatus accountVerificationStatus;
    private Boolean isIdentityConfirmed;
    private Boolean isEmailVerified;
    private Boolean isPhoneVerified;

    // Novos campos para os termos aceitos
    private Boolean acceptTerms;
    private String acceptedTermsVersion; // Versão dos termos de uso aceitos
    private LocalDate termsAcceptanceDate; // Data de aceitação dos termos de uso
    private Boolean isTermsOfUseOutdated; // Indica se os termos de uso estão desatualizados

    private Boolean agreeToLgpdTerms;
    private String acceptedLgpdTermsVersion; // Versão dos termos LGPD aceitos
    private LocalDate lgpdTermsAcceptanceDate; // Data de aceitação dos termos LGPD
    private Boolean isLgpdTermsOutdated; // Indica se os termos LGPD estão desatualizados

    private Boolean acceptPrivacyPolicy; // Novo campo
    private String acceptedPrivacyPolicyVersion; // Versão da política de privacidade aceita
    private LocalDate privacyPolicyAcceptanceDate; // Data de aceitação da política de privacidade
    private Boolean isPrivacyPolicyOutdated; // Indica se a política de privacidade está desatualizada

    private Boolean authorizeCreditCheckAndCommunication;
    private Boolean acceptMarketingCommunications;
}
