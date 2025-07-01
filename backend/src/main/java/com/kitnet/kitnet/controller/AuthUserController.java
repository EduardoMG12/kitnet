package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.legalDocument.LegalDocumentDTO;
import com.kitnet.kitnet.dto.user.*;
import com.kitnet.kitnet.exception.FirebaseAuthenticationException;
import com.kitnet.kitnet.mapper.LegalDocumentMapper;
import com.kitnet.kitnet.mapper.UserMapper;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.service.UserService;
import com.kitnet.kitnet.service.UserVerificationDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register-simple")
    public ResponseEntity<AuthResponseWithTermsDTO> registerSimple(@RequestBody @Valid UserSimpleRegisterDTO dto) throws Exception {
        AuthResponseWithTermsDTO registeredUserEntity = userService.registerSimple(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserEntity);
    }

    @PostMapping("/firebase-login")
    public ResponseEntity<AuthResponseDTO> firebaseLogin(
            @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthenticationException, Exception {
        String idToken = authorizationHeader.substring(7);
        AuthResponseDTO response = userService.authenticateWithFirebase(idToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid UserLoginDTO dto) throws Exception {
        AuthResponseDTO loggedInUser = userService.login(dto);
        return ResponseEntity.ok(loggedInUser);
    }

    @PutMapping("/complete-register")
    public ResponseEntity<CompleteRegistrationResponseDTO> completeRegistration(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestBody @Valid UserCompleteRegistrationDTO dto) throws Exception {

        User updatedUser = userService.completeRegistrationDetails(authenticatedUser.getId(), dto);

        List<LegalDocumentDTO> allAcceptedLegalDocumentsDTO = updatedUser.getUserLegalDocuments().stream()
                .filter(uld -> uld.getAcceptanceDate().equals(LocalDate.now()))
                .map(uld -> LegalDocumentMapper.toLegalDocumentDTO(uld.getLegalDocument()))
                .collect(Collectors.toList());

        UserCompleteResponseDTO userResponse = UserMapper.toCompleteUserResponseDTO(updatedUser);

        return ResponseEntity.ok(new CompleteRegistrationResponseDTO(userResponse, allAcceptedLegalDocumentsDTO));
    }
}