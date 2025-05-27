package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.user.*;
import com.kitnet.kitnet.model.RoleName;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.VerificationStatus; // Importe o VerificationStatus
import com.kitnet.kitnet.exception.EmailAlreadyInUseException;
import com.kitnet.kitnet.exception.InvalidCredentialsException;
import com.kitnet.kitnet.exception.PasswordMismatchException;
import com.kitnet.kitnet.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    AuthResponseDTO registerSimple(UserSimpleRegisterDTO dto) throws EmailAlreadyInUseException, PasswordMismatchException;
    AuthResponseDTO login(UserLoginDTO dto) throws UserNotFoundException, InvalidCredentialsException;
    User updateProfile(UUID userId, UserProfileUpdateDTO dto) throws UserNotFoundException, EmailAlreadyInUseException;

    // Methods to begin and complete verifications of email and phone
    User initiateEmailVerification(UUID userId) throws UserNotFoundException;
    User completeEmailVerification(UUID userId, String code) throws UserNotFoundException, InvalidCredentialsException;
    User initiatePhoneVerification(UUID userId) throws UserNotFoundException;
    User completePhoneVerification(UUID userId, String code) throws UserNotFoundException, InvalidCredentialsException;


    User addRoleToUser(UUID userId, RoleName roleName);
    User removeRoleFromUser(UUID userId, RoleName roleName);


    // Methods to administrator/moderator
    User setVerificationStatus(UUID userId, VerificationStatus status) throws UserNotFoundException;
    User setIncomeAndCreditStatus(UUID userId, Double income, Boolean hasCreditRestrictions) throws UserNotFoundException;
    List<User> findUsersForVerification(VerificationStatus status);
    User findById(UUID id) throws UserNotFoundException;
}