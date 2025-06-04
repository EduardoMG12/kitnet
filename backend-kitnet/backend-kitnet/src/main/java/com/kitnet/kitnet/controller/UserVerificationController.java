package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.UserVerificationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users/verify")
public class UserVerificationController {

    @Autowired
    private UserVerificationDataService userVerificationService;

    @PostMapping("/email/initiate")
    public ResponseEntity<EmailVerificationResponseDTO> initiateEmailVerification(@AuthenticationPrincipal User authenticatedUser) {
        EmailVerificationResponseDTO response = userVerificationService.initiateEmailVerification(authenticatedUser.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/confirm")
    public ResponseEntity<EmailVerificationResponseDTO> confirmEmailVerification(@RequestParam("token") String token) {
        EmailVerificationResponseDTO response = userVerificationService.confirmEmailVerification(token);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/phone/initiate")
//    public ResponseEntity<String> initiatePhoneVerification(@AuthenticationPrincipal User authenticatedUser) {}

//    @PostMapping("/phone/confirm")
//    public ResponseEntity<UserResponseDTO> completePhoneVerification(@AuthenticationPrincipal User authenticatedUser,
//                                                                     @RequestParam String code) {}
}
