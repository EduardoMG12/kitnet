package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.user.*;
import com.kitnet.kitnet.exception.*;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    AuthResponseWithTermsDTO registerSimple(UserSimpleRegisterDTO dto) throws EmailAlreadyInUseException, PasswordMismatchException;
    User completeRegistrationDetails(UUID userId, UserCompleteRegistrationDTO dto) throws UserNotFoundException, EmailAlreadyInUseException, IOException;
    AuthResponseDTO login(UserLoginDTO dto) throws UserNotFoundException, InvalidCredentialsException;
    User updateProfile(UUID userId, UserProfileUpdateDTO dto) throws UserNotFoundException, EmailAlreadyInUseException;
    AuthResponseDTO authenticateWithFirebase(String firebaseIdToken) throws FirebaseAuthenticationException, UserNotFoundException;

    String updateProfilePicture(UUID userId, MultipartFile file) throws UserNotFoundException, IOException, FileUploadException, InvalidFileFormatException, FileSizeExceededException;

    // Methods to administrator/moderator
    User setVerificationStatus(UUID userId, VerificationStatus status) throws UserNotFoundException;
    User setIncomeAndCreditStatus(UUID userId, Double income, Boolean hasCreditRestrictions) throws UserNotFoundException;
    List<User> findUsersForVerification(VerificationStatus status);
    User findById(UUID id) throws UserNotFoundException;
}