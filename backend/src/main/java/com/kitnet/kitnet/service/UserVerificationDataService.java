package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.MessageResponseDTO;
import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.dto.passwordReset.PasswordResetConfirmDTO;
import com.kitnet.kitnet.exception.*;

import java.util.UUID;

public interface UserVerificationDataService {
    // Email verification
    EmailVerificationResponseDTO initiateEmailVerification(UUID userId) throws UserNotFoundException, InvalidOperationException;
    EmailVerificationResponseDTO confirmEmailVerification(String token) throws InvalidVerificationTokenException, InvalidOperationException;

    // Password Reset
    MessageResponseDTO initiatePasswordReset(String email) throws UserNotFoundException, EmailSendException, InternalServerErrorException, InvalidOperationException;
    MessageResponseDTO confirmPasswordReset(String token, PasswordResetConfirmDTO dto) throws InvalidVerificationTokenException, InvalidOperationException,InvalidCredentialsException, UserNotFoundException;

    //  Phone verification
    //    User initiatePhoneVerification(UUID userId) throws UserNotFoundException;
    //    User completePhoneVerification(UUID userId, String code) throws UserNotFoundException, InvalidCredentialsException;
}
