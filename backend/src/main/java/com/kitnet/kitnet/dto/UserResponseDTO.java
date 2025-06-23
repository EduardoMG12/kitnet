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
    
    private Boolean acceptTerms;
    private String acceptedTermsVersion;
    private LocalDate termsAcceptanceDate;
    private Boolean isTermsOfUseOutdated;

    private Boolean agreeToLgpdTerms;
    private String acceptedLgpdTermsVersion;
    private LocalDate lgpdTermsAcceptanceDate;
    private Boolean isLgpdTermsOutdated;

    private Boolean acceptPrivacyPolicy;
    private String acceptedPrivacyPolicyVersion;
    private LocalDate privacyPolicyAcceptanceDate;
    private Boolean isPrivacyPolicyOutdated;

    private Boolean authorizeCreditCheckAndCommunication;
    private Boolean acceptMarketingCommunications;
}
