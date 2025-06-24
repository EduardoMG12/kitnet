package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.upload.UserProfilePictureUploadResponseDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    @Autowired
    private UserService userService;

    @PostMapping("/profile-picture")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfilePictureUploadResponseDTO> uploadUserProfilePicture(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestParam("file") MultipartFile file) throws IOException, Exception {
        String imageUrl = userService.updateProfilePicture(authenticatedUser.getId(), file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserProfilePictureUploadResponseDTO("Profile picture uploaded successfully.", imageUrl, authenticatedUser.getId().toString()));
    }
}
