package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.exception.InvalidVerificationTokenException;
import com.kitnet.kitnet.exception.UserNotFoundException;

import java.util.UUID;

public interface UserVerificationDataService {
    // Email verification
    EmailVerificationResponseDTO initiateEmailVerification(UUID userId) throws UserNotFoundException;
    EmailVerificationResponseDTO confirmEmailVerification(String token) throws InvalidVerificationTokenException;

    //  Phone verification
    //    User initiatePhoneVerification(UUID userId) throws UserNotFoundException;
    //    User completePhoneVerification(UUID userId, String code) throws UserNotFoundException, InvalidCredentialsException;
}
