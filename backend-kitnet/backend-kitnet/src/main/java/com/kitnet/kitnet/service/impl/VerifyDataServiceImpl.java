package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.exception.EmailSendException;
import com.kitnet.kitnet.exception.InvalidVerificationTokenException;
import com.kitnet.kitnet.exception.UserNotFoundException;
import com.kitnet.kitnet.model.EmailVerificationToken;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.UserService;
import com.kitnet.kitnet.service.VerifyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

public class VerifyDataServiceImpl implements VerifyDataService {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    //criar uma funcao generica por buscar o usuario por id e criar um token de numeros de 6 digitos, apos isso creio q a melhor saida seria criar dois outros services para o sms e para o email para manter o codigo desacoplado pois cada um desses jeitos tem sua implementacao especifica, dai apos isso so chamar estes services aqui, queria mandar o email por alguma api gratuita como api do google ou apo do outlook ou outra, se quiser mudar o nome das funcoes do service fique a vontade
    @Override
    @Transactional
    public EmailVerificationResponseDTO initiateEmailVerification(UUID userId) throws UserNotFoundException {

        User user = userService.findById(userId);

        if (user.getIsEmailVerified()) {
            throw new IllegalStateException(
                    messageSource.getMessage("error.email.already.verified", null, LocaleContextHolder.getLocale()));
        }

        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        verificationToken.setUsed(false);

        emailVerificationTokenRepository.save(verificationToken);

        String verificationLink = baseUrl + "/api/auth/verify-email/confirm?token=" + token;

        try {
            emailService.sendVerificationEmail(user.getEmail(), user.getName(), "VERIFY_EMAIL", verificationLink);
            return new EmailVerificationResponseDTO(
                    messageSource.getMessage("success.email.verification.initiated", null, LocaleContextHolder.getLocale()),
                    false
            );
        } catch (Exception e) {
            throw new EmailSendException(
                    messageSource.getMessage("error.email.send.failed", null, LocaleContextHolder.getLocale()), e);
        }
    }

    @Override
    @Transactional
    public EmailVerificationResponseDTO confirmEmailVerification(String token) {

        EmailVerificationToken tokenEntity = emailVerificationTokenRepository.findByTokenAndUsedFalse(token)
                .orElseThrow(() -> {
                    return new InvalidVerificationTokenException("Token inválido ou expirado");
                });

        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidVerificationTokenException("Token inválido ou expirado");
        }

        User user = tokenEntity.getUser();
        user.setIsEmailVerified(true);
        userRepository.save(user);

        tokenEntity.setUsed(true);
        emailVerificationTokenRepository.save(tokenEntity);

        return new EmailVerificationResponseDTO(
                messageSource.getMessage("success.email.verified", null, LocaleContextHolder.getLocale()),
                true
        );
    }
}
