package com.kitnet.kitnet.dto.user;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserCompleteRegisterResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String profilePictureUrl;
    private Set<String> roles;
    private String accountVerificationStatus;
    private Boolean isIdentityConfirmed;
    private Boolean isEmailVerified;
    private Boolean isPhoneVerified;
    private String legalPersonType;
    private Double monthlyGrossIncome;
    private Boolean hasCreditRestrictions;
}

