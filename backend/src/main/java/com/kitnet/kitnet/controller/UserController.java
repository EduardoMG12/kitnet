package com.kitnet.kitnet.controller;


import com.kitnet.kitnet.dto.UserResponseDTO;
import com.kitnet.kitnet.dto.upload.UserProfilePictureUploadResponseDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/me/profile-picture")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfilePictureUploadResponseDTO> uploadUserProfilePicture(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestParam("file") MultipartFile file) throws IOException, Exception {
        String imageUrl = userService.updateProfilePicture(authenticatedUser.getId(), file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserProfilePictureUploadResponseDTO("Profile picture uploaded successfully.", imageUrl, authenticatedUser.getId().toString()));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUserProfile(@AuthenticationPrincipal User authenticatedUser) throws Exception {
        User user = userService.findById(authenticatedUser.getId());
        return ResponseEntity.ok(toUserResponseDTO(user)); // Reuse existing mapping
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
        dto.setAccountVerificationStatus(user.getAccountVerificationStatus());
        dto.setIsIdentityConfirmed(user.getIsIdentityConfirmed());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setIsPhoneVerified(user.getIsPhoneVerified());
        return dto;
    }
}