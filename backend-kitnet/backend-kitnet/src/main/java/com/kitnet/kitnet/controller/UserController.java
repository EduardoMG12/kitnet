package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.emailVerification.EmailVerificationRequestDTO;
import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.dto.user.*;
import com.kitnet.kitnet.dto.UserResponseDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.CustomUserDetailsService;
import com.kitnet.kitnet.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService; // Provavelmente não é necessário aqui, mas manter por enquanto

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register-simple")
    public ResponseEntity<AuthResponseDTO> registerSimple(@RequestBody @Valid UserSimpleRegisterDTO dto) throws Exception {
        AuthResponseDTO registeredUser = userService.registerSimple(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
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

    @PostMapping("/verify-email/initiate")
    public ResponseEntity<EmailVerificationResponseDTO> initiateEmailVerification(@AuthenticationPrincipal User authenticatedUser) {
        EmailVerificationResponseDTO response = userService.initiateEmailVerification(authenticatedUser.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email/confirm")
    public ResponseEntity<EmailVerificationResponseDTO> confirmEmailVerification(@RequestParam("token") String token) {
        EmailVerificationResponseDTO response = userService.confirmEmailVerification(token);
        return ResponseEntity.ok(response);
    }
//    // Endpoint para iniciar verificação de telefone
//    @PostMapping("/verify-phone/initiate")
//    public ResponseEntity<String> initiatePhoneVerification(@AuthenticationPrincipal User authenticatedUser) {
//        try {
//            userService.initiatePhoneVerification(authenticatedUser.getId());
//            return ResponseEntity.ok("Código de verificação de telefone enviado (simulado).");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // Endpoint para completar verificação de telefone
//    @PostMapping("/verify-phone/complete")
//    public ResponseEntity<UserResponseDTO> completePhoneVerification(@AuthenticationPrincipal User authenticatedUser,
//                                                                     @RequestParam String code) {
//        try {
//            User updatedUser = userService.completePhoneVerification(authenticatedUser.getId(), code);
//            return ResponseEntity.ok(toUserResponseDTO(updatedUser));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

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