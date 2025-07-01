package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.MessageResponseDTO;
import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.dto.passwordReset.PasswordResetConfirmDTO;
import com.kitnet.kitnet.exception.*;
import com.kitnet.kitnet.model.EmailVerificationToken;
import com.kitnet.kitnet.model.PasswordResetToken;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.AuthProvider;
import com.kitnet.kitnet.repository.PasswordResetTokenRepository;
import com.kitnet.kitnet.repository.UserVerificationRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.EmailService;
import com.kitnet.kitnet.service.UserService;
import com.kitnet.kitnet.service.UserVerificationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserVerificationDataServiceImpl implements UserVerificationDataService {

    @Autowired
    private UserVerificationRepository userVerificationRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    @Value("${app.token.expiry-minutes:15}")
    private long tokenExpiryMinutes;

    @Override
    @Transactional
    public EmailVerificationResponseDTO initiateEmailVerification(UUID userId) throws UserNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.not.found", null, locale)));

        if (user.getAuthProvider() != AuthProvider.EMAIL_PASSWORD) {
            throw new InvalidOperationException(messageSource.getMessage("error.email.verification.social.account", null, locale));
        }

        if (user.getIsEmailVerified()) {
            throw new EmailAlreadyVerifiedException(
                    messageSource.getMessage("error.email.already.verified", null, locale));
        }

        Optional<EmailVerificationToken> existingToken = userVerificationRepository
                .findByUserAndUsedFalseAndExpiryDateAfter(user, LocalDateTime.now());

        if (existingToken.isPresent()) {
            throw new VerificationEmailAlreadySentException(
                    messageSource.getMessage("error.email.verification.already.sent", null, locale)
            );
        }

        userVerificationRepository.findByUserAndUsedFalseAndExpiryDateAfter(user, LocalDateTime.now())
                .ifPresent(token -> userVerificationRepository.delete(token));


        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(tokenExpiryMinutes));
        verificationToken.setUsed(false);

        userVerificationRepository.save(verificationToken);

        String verificationLink = baseUrl + "/api/users/verify/email/confirm?token=" + token;

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("userName", user.getName());
        templateVariables.put("verificationLink", verificationLink);
        templateVariables.put("tokenExpiryMinutes", tokenExpiryMinutes);

        try {
            emailService.sendEmail(user.getEmail(), "Verifique seu E-mail - Kitnet", "verification_email_v2", templateVariables);
            return new EmailVerificationResponseDTO(
                    messageSource.getMessage("success.email.verification.initiated", null, locale),
                    false
            );
        } catch (EmailSendException e) {
            throw  e;
        } catch (IOException e) {
            throw new EmailSendException(messageSource.getMessage("error.email.template.processing.failed", null, locale), e);
        }
    }

    @Override
    @Transactional
    public EmailVerificationResponseDTO confirmEmailVerification(String token) {
        Locale locale = LocaleContextHolder.getLocale();

        EmailVerificationToken tokenEntity = userVerificationRepository.findByTokenAndUsedFalse(token)
                .orElseThrow(() -> {
                    return new InvalidVerificationTokenException(messageSource.getMessage("error.invalid.verification.token", null, locale));
                });

        User user = tokenEntity.getUser();
        if (user.getIsEmailVerified()) {
            tokenEntity.setUsed(true);
            userVerificationRepository.save(tokenEntity);
            throw new InvalidVerificationTokenException(messageSource.getMessage("error.invalid.verification.token", null, locale));
        }


        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenEntity.setUsed(true);
            userVerificationRepository.save(tokenEntity);
            throw new InvalidVerificationTokenException(messageSource.getMessage("error.invalid.verification.token", null, locale));
        }

        user.setIsEmailVerified(true);
        userRepository.save(user);

        tokenEntity.setUsed(true);
        userVerificationRepository.save(tokenEntity);

        return new EmailVerificationResponseDTO(
                messageSource.getMessage("success.email.verified", null, locale),
                true
        );
    }



    @Override
    @Transactional
    public MessageResponseDTO initiatePasswordReset(String email) throws UserNotFoundException, EmailSendException, InternalServerErrorException {
        Locale locale = LocaleContextHolder.getLocale();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        messageSource.getMessage("error.user.not.found", null, locale)));

        if (user.getAuthProvider() != AuthProvider.EMAIL_PASSWORD) {
            throw new InvalidOperationException(messageSource.getMessage("error.password.reset.social.account", null, locale));
        }

        passwordResetTokenRepository.findByUserAndUsedFalseAndExpiryDateAfter(user, LocalDateTime.now())
                .ifPresent(oldToken -> {
                    oldToken.setUsed(true);
                    passwordResetTokenRepository.save(oldToken);
                });


        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(tokenExpiryMinutes));
        resetToken.setUsed(false);

        passwordResetTokenRepository.save(resetToken);

        // Link para o frontend (página de redefinição de senha)
        String resetPasswordLink = baseUrl + "/api/users/verify/password/reset?token=" + token;

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("userName", user.getName());
        templateVariables.put("resetPasswordLink", resetPasswordLink);
        templateVariables.put("tokenExpiryMinutes", tokenExpiryMinutes);

        try {
            emailService.sendEmail(user.getEmail(), messageSource.getMessage("email.subject.password.reset", null, locale), "password_reset_email", templateVariables);
            return new MessageResponseDTO(
                    messageSource.getMessage("success.password.reset.initiated", null, locale)
            );
        } catch (EmailSendException e) {
            throw e;
        } catch (IOException e) {
            throw new InternalServerErrorException(messageSource.getMessage("error.email.template.processing.failed", null, locale), e);
        }
    }

    @Override
    @Transactional
    public MessageResponseDTO confirmPasswordReset(String token, PasswordResetConfirmDTO dto) throws InvalidVerificationTokenException, InvalidCredentialsException, UserNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new InvalidCredentialsException(
                    messageSource.getMessage("error.password.mismatch", null, locale));
        }

        PasswordResetToken tokenEntity = passwordResetTokenRepository
                .findByTokenAndUsedFalseAndExpiryDateAfter(token, LocalDateTime.now())
                .orElseThrow(() -> new InvalidVerificationTokenException(
                        messageSource.getMessage("error.invalid.verification.token", null, locale)));

        User user = tokenEntity.getUser();

        if (user == null) {
            tokenEntity.setUsed(true);
            passwordResetTokenRepository.save(tokenEntity);
            throw new UserNotFoundException(messageSource.getMessage("error.user.not.found", null, locale));
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        tokenEntity.setUsed(true);
        passwordResetTokenRepository.save(tokenEntity);

        return new MessageResponseDTO(
                messageSource.getMessage("success.password.reset.confirmed", null, locale)
        );
    }
}