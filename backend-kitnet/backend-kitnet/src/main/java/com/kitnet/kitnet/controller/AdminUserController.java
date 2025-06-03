package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.UserResponseDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import com.kitnet.kitnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/needs-verification")
    public ResponseEntity<List<UserResponseDTO>> getUsersNeedingVerification(@RequestParam(required = false) VerificationStatus status) {
        List<User> users;
        if (status == null) {
            // Se nenhum status for especificado, lista NOT_SUBMITTED e PENDING
            users = userService.findUsersForVerification(VerificationStatus.NOT_SUBMITTED);
            users.addAll(userService.findUsersForVerification(VerificationStatus.PENDING));
        } else {
            users = userService.findUsersForVerification(status);
        }
        return ResponseEntity.ok(users.stream().map(this::toUserResponseDTO).collect(Collectors.toList()));
    }

    // Endpoint para aprovar/rejeitar/definir status de verificação de um usuário
    @PutMapping("/{userId}/verify-status")
    public ResponseEntity<UserResponseDTO> setVerificationStatus(@PathVariable UUID userId, @RequestParam VerificationStatus status) {
        try {
            User updatedUser = userService.setVerificationStatus(userId, status);
            return ResponseEntity.ok(toUserResponseDTO(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Use um DTO de erro real aqui
        }
    }

    // Endpoint para definir renda e restrições de crédito
    @PutMapping("/{userId}/financial-data")
    public ResponseEntity<UserResponseDTO> setFinancialData(@PathVariable UUID userId,
                                                            @RequestParam Double income,
                                                            @RequestParam Boolean hasCreditRestrictions) {
        try {
            User updatedUser = userService.setIncomeAndCreditStatus(userId, income, hasCreditRestrictions);
            return ResponseEntity.ok(toUserResponseDTO(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Use um DTO de erro real aqui
        }
    }

    private UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setRoles(user.getRoles().stream()
                .map(role -> role.getName())
                .collect(java.util.stream.Collectors.toSet()));
//        dto.setLegalPersonType(user.getLegalPersonType());
//        dto.setLegalDocument(user.getLegalDocument());
        dto.setAccountVerificationStatus(user.getAccountVerificationStatus());
//        dto.setMonthlyGrossIncome(user.getMonthlyGrossIncome());
//        dto.setHasCreditRestrictions(user.getHasCreditRestrictions());
        dto.setIsIdentityConfirmed(user.getIsIdentityConfirmed());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setIsPhoneVerified(user.getIsPhoneVerified());
        return dto;
    }
}