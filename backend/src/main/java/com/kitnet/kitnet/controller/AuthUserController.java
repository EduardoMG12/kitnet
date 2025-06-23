package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.dto.user.*;
import com.kitnet.kitnet.dto.UserResponseDTO;
import com.kitnet.kitnet.exception.FirebaseAuthenticationException;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.CustomUserDetailsService;
import com.kitnet.kitnet.service.UserService;
import com.kitnet.kitnet.service.UserVerificationDataService;
import com.kitnet.kitnet.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserVerificationDataService userVerificationDataService;

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private CustomUserDetailsService userDetailsService; // Probably not needed here, but keep for now
//
//    @Autowired
//    private JwtUtil jwtUtil;


    @PostMapping("/register-simple")
    public ResponseEntity<AuthResponseDTO> registerSimple(@RequestBody @Valid UserSimpleRegisterDTO dto) throws Exception {
        AuthResponseDTO registeredUser = userService.registerSimple(dto);
        userVerificationDataService.initiateEmailVerification(registeredUser.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/firebase-login")
    public ResponseEntity<AuthResponseDTO> firebaseLogin(
            @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthenticationException, Exception {
        String idToken = authorizationHeader.substring(7); // Remove "Bearer "
        AuthResponseDTO response = userService.authenticateWithFirebase(idToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid UserLoginDTO dto) throws Exception {
        AuthResponseDTO loggedInUser = userService.login(dto);
        return ResponseEntity.ok(loggedInUser);
    }

    @PutMapping("/complete-register")
    public ResponseEntity<UserResponseDTO> completeRegistration(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestBody @Valid UserCompleteRegistrationDTO dto) throws Exception {
        User updatedUser = userService.completeRegistrationDetails(authenticatedUser.getId(), dto);
        return ResponseEntity.ok(toUserResponseDTO(updatedUser));
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