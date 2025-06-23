package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.MessageResponseDTO;
import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.dto.passwordReset.PasswordResetConfirmDTO;
import com.kitnet.kitnet.dto.passwordReset.PasswordResetRequestDTO;
import com.kitnet.kitnet.exception.InvalidVerificationTokenException;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.UserVerificationDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("api/users/verify")
public class UserVerificationController {

    @Autowired
    private UserVerificationDataService userVerificationService;

    @Autowired
    private MessageSource messageSource;

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

    @PostMapping("/password/request")
    public ResponseEntity<MessageResponseDTO> requestPasswordReset(@RequestBody @Valid PasswordResetRequestDTO dto) {
        MessageResponseDTO response = userVerificationService.initiatePasswordReset(dto.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<MessageResponseDTO> resetPassword(
            @RequestParam("token") String urlToken,
            @RequestBody @Valid PasswordResetConfirmDTO dto) {

        String finalToken = urlToken;
        if (finalToken == null || finalToken.isEmpty()) {
             finalToken = dto.getToken();
            if (finalToken == null || finalToken.isEmpty()) {
                Locale locale = LocaleContextHolder.getLocale();
                new InvalidVerificationTokenException(
                        messageSource.getMessage("error.invalid.verification.token", null, locale));
            }
        }
        MessageResponseDTO response = userVerificationService.confirmPasswordReset(finalToken, dto);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/phone/initiate")
//    public ResponseEntity<String> initiatePhoneVerification(@AuthenticationPrincipal User authenticatedUser) {}

//    @PostMapping("/phone/confirm")
//    public ResponseEntity<UserResponseDTO> completePhoneVerification(@AuthenticationPrincipal User authenticatedUser,
//                                                                     @RequestParam String code) {}
}
