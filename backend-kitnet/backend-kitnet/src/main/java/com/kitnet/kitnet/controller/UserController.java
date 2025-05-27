package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.user.AuthResponseDTO;
import com.kitnet.kitnet.dto.user.UserLoginDTO;
import com.kitnet.kitnet.dto.user.UserSimpleRegisterDTO;
import com.kitnet.kitnet.dto.user.UserProfileUpdateDTO;
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

    // --- Novos Endpoints para Perfil e Verificação ---

    // Endpoint para completar/atualizar o perfil do usuário logado
    @PutMapping("/profile") // Rota para o próprio usuário atualizar seu perfil
    public ResponseEntity<UserResponseDTO> updateProfile(@AuthenticationPrincipal User authenticatedUser,
                                                         @RequestBody @Valid UserProfileUpdateDTO dto) {
        try {
            User updatedUser = userService.updateProfile(authenticatedUser.getId(), dto);
            return ResponseEntity.ok(toUserResponseDTO(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Tratar com DTO de erro
        }
    }

    // Endpoint para iniciar verificação de e-mail
    @PostMapping("/verify-email/initiate")
    public ResponseEntity<String> initiateEmailVerification(@AuthenticationPrincipal User authenticatedUser) {
        try {
            userService.initiateEmailVerification(authenticatedUser.getId());
            return ResponseEntity.ok("Código de verificação de e-mail enviado (simulado).");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para completar verificação de e-mail
    @PostMapping("/verify-email/complete")
    public ResponseEntity<UserResponseDTO> completeEmailVerification(@AuthenticationPrincipal User authenticatedUser,
                                                                     @RequestParam String code) {
        try {
            User updatedUser = userService.completeEmailVerification(authenticatedUser.getId(), code);
            return ResponseEntity.ok(toUserResponseDTO(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint para iniciar verificação de telefone
    @PostMapping("/verify-phone/initiate")
    public ResponseEntity<String> initiatePhoneVerification(@AuthenticationPrincipal User authenticatedUser) {
        try {
            userService.initiatePhoneVerification(authenticatedUser.getId());
            return ResponseEntity.ok("Código de verificação de telefone enviado (simulado).");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para completar verificação de telefone
    @PostMapping("/verify-phone/complete")
    public ResponseEntity<UserResponseDTO> completePhoneVerification(@AuthenticationPrincipal User authenticatedUser,
                                                                     @RequestParam String code) {
        try {
            User updatedUser = userService.completePhoneVerification(authenticatedUser.getId(), code);
            return ResponseEntity.ok(toUserResponseDTO(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
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
        dto.setAccountVerificationStatus(user.getAccountVerificationStatus());
        dto.setIsIdentityConfirmed(user.getIsIdentityConfirmed());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setIsPhoneVerified(user.getIsPhoneVerified());
        return dto;
    }
}