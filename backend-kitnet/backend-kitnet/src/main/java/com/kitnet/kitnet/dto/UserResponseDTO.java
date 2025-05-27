package com.kitnet.kitnet.dto;

import com.kitnet.kitnet.model.LegalPersonType;
import com.kitnet.kitnet.model.RoleName;
import com.kitnet.kitnet.model.VerificationStatus;
import lombok.Data;

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
    private LegalPersonType legalPersonType;
    private String legalDocument;
    private VerificationStatus accountVerificationStatus;
    private Double monthlyGrossIncome;
    private Boolean hasCreditRestrictions;
    private Boolean isIdentityConfirmed;
    private Boolean isEmailVerified;
    private Boolean isPhoneVerified;
}