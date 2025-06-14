package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.emailVerification.EmailVerificationResponseDTO;
import com.kitnet.kitnet.dto.user.*;
import com.kitnet.kitnet.exception.*;
import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.VerificationStatus; // Importe o VerificationStatus

import java.util.List;
import java.util.UUID;

public interface UserService {
    AuthResponseDTO registerSimple(UserSimpleRegisterDTO dto) throws EmailAlreadyInUseException, PasswordMismatchException;
    User completeRegistrationDetails(UUID userId, UserCompleteRegistrationDTO dto) throws UserNotFoundException, EmailAlreadyInUseException;
    AuthResponseDTO login(UserLoginDTO dto) throws UserNotFoundException, InvalidCredentialsException;
    User updateProfile(UUID userId, UserProfileUpdateDTO dto) throws UserNotFoundException, EmailAlreadyInUseException;
    AuthResponseDTO authenticateWithFirebase(String firebaseIdToken) throws FirebaseAuthenticationException, UserNotFoundException;


//    User addRoleToUser(UUID userId, RoleName roleName);
//    User removeRoleFromUser(UUID userId, RoleName roleName);


    // Methods to administrator/moderator
    User setVerificationStatus(UUID userId, VerificationStatus status) throws UserNotFoundException;
    User setIncomeAndCreditStatus(UUID userId, Double income, Boolean hasCreditRestrictions) throws UserNotFoundException;
    List<User> findUsersForVerification(VerificationStatus status);
    User findById(UUID id) throws UserNotFoundException;
}