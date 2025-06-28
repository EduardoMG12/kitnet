package com.kitnet.kitnet.mapper;

import com.kitnet.kitnet.dto.user.UserCompleteResponseDTO;
import com.kitnet.kitnet.dto.user.UserSimpleResponseDTO;
import com.kitnet.kitnet.model.User;

import java.util.HashSet;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserSimpleResponseDTO toUserSimpleResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        UserSimpleResponseDTO dto = new UserSimpleResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(role -> role.getName().toString())
                    .collect(Collectors.toSet()));
        } else {
            dto.setRoles(new HashSet<>());
        }
        if (user.getAccountVerificationStatus() != null) {
            dto.setAccountVerificationStatus(user.getAccountVerificationStatus().toString());
        }
        dto.setIsIdentityConfirmed(user.getIsIdentityConfirmed());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setIsPhoneVerified(user.getIsPhoneVerified());
        return dto;
    }

    public static UserCompleteResponseDTO toCompleteUserResponseDTO(User user) {
        if (user == null) return null;
        UserCompleteResponseDTO dto = new UserCompleteResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setProfession(user.getProfession());
        dto.setEmergencyContactName(user.getEmergencyContactName());
        dto.setEmergencyContactPhone(user.getEmergencyContactPhone());

        dto.setRoles(user.getRoles() != null ? user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toSet()) : new HashSet<>());
        dto.setAccountVerificationStatus(user.getAccountVerificationStatus() != null ? user.getAccountVerificationStatus().toString() : null);
        dto.setIsIdentityConfirmed(user.getIsIdentityConfirmed());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setIsPhoneVerified(user.getIsPhoneVerified());
        dto.setAuthorizeCreditCheckAndCommunication(user.getAuthorizeCreditCheckAndCommunication());
        dto.setAcceptMarketingCommunications(user.getAcceptMarketingCommunications());
        return dto;
    }
}
