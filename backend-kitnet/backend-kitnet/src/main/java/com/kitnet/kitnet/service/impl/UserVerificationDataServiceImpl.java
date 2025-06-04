package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.exception.*;
import com.kitnet.kitnet.model.EmailVerificationToken;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.repository.UserVerificationRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.EmailService;
import com.kitnet.kitnet.service.UserService;
import com.kitnet.kitnet.service.UserVerificationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserVerificationDataServiceImpl implements UserVerificationDataService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserVerificationRepository userVerificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    @Value("${app.token.expiry-minutes:15}")
    private long tokenExpiryMinutes;

    @Override
    @Transactional
    public EmailVerificationResponseDTO initiateEmailVerification(UUID userId) throws UserNotFoundException {
        User user = userService.findById(userId);

        if (user.getIsEmailVerified()) {
            throw new EmailAlreadyVerifiedException(
                    messageSource.getMessage("error.email.already.verified", null, LocaleContextHolder.getLocale()));
        }

        Optional<EmailVerificationToken> existingToken = userVerificationRepository
                .findByUserAndUsedFalseAndExpiryDateAfter(user, LocalDateTime.now());

        if (existingToken.isPresent()) {
            throw new VerificationEmailAlreadySentException(
                    messageSource.getMessage("error.email.verification.already.sent", null, LocaleContextHolder.getLocale())
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
                    messageSource.getMessage("success.email.verification.initiated", null, LocaleContextHolder.getLocale()),
                    false
            );
        } catch (EmailSendException e) {
            throw  e;
        } catch (IOException e) {
            throw new EmailSendException(messageSource.getMessage("error.email.template.processing.failed", null, LocaleContextHolder.getLocale()), e);
        }
    }

    @Override
    @Transactional
    public EmailVerificationResponseDTO confirmEmailVerification(String token) {
        EmailVerificationToken tokenEntity = userVerificationRepository.findByTokenAndUsedFalse(token)
                .orElseThrow(() -> {
                    return new InvalidVerificationTokenException(messageSource.getMessage("error.invalid.verification.token", null, LocaleContextHolder.getLocale()));
                });

        User user = tokenEntity.getUser();
        if (user.getIsEmailVerified()) {
            tokenEntity.setUsed(true);
            userVerificationRepository.save(tokenEntity);
            throw new InvalidVerificationTokenException(messageSource.getMessage("error.invalid.verification.token", null, LocaleContextHolder.getLocale()));
        }


        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenEntity.setUsed(true);
            userVerificationRepository.save(tokenEntity);
            throw new InvalidVerificationTokenException(messageSource.getMessage("error.invalid.verification.token", null, LocaleContextHolder.getLocale()));
        }

        user.setIsEmailVerified(true);
        userRepository.save(user);

        tokenEntity.setUsed(true);
        userVerificationRepository.save(tokenEntity);

        return new EmailVerificationResponseDTO(
                messageSource.getMessage("success.email.verified", null, LocaleContextHolder.getLocale()),
                true
        );
    }
}