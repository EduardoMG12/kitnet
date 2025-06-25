package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.UserDocument;
import com.kitnet.kitnet.model.enums.DocumentType;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import com.kitnet.kitnet.service.UserDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-documents")
public class UserDocumentController {

    @Autowired
    private UserDocumentService userDocumentService;

    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, String>>> uploadUserDocuments(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("types") List<DocumentType> documentTypes) throws Exception {

        List<UserDocument> uploadedDocs = userDocumentService.uploadMultipleVerificationDocuments(
                authenticatedUser.getId(), files, documentTypes, authenticatedUser.getId());

        List<Map<String, String>> response = uploadedDocs.stream().map(doc -> {
            Map<String, String> docInfo = new HashMap<>();
            docInfo.put("documentId", doc.getId().toString());
            docInfo.put("documentType", doc.getDocumentType().toString());
            docInfo.put("documentUrl", doc.getCurrentVersion().map(v -> v.getDocumentUrl()).orElse("N/A"));
            docInfo.put("status", doc.getCurrentVersion().map(v -> v.getVerificationStatus().toString()).orElse("N/A"));
            return docInfo;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("isAuthenticated() and (authentication.principal.id == #userId or hasRole('ADMIN') or hasRole('MODERATOR'))")
    public ResponseEntity<List<UserDocument>> getUserDocuments(@AuthenticationPrincipal User authenticatedUser, @PathVariable UUID userId) throws Exception {
        List<UserDocument> documents = userDocumentService.getUserDocuments(userId);
        return ResponseEntity.ok(documents);
    }


    @PutMapping("/{documentId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<UserDocument> updateDocumentStatus(
            @AuthenticationPrincipal User authenticatedUser,
            @PathVariable UUID documentId,
            @RequestParam VerificationStatus status,
            @RequestParam(required = false) String rejectionReason) throws Exception {

        UserDocument updatedDoc = userDocumentService.updateDocumentVerificationStatus(
                documentId, status, rejectionReason, authenticatedUser.getId());
        return ResponseEntity.ok(updatedDoc);
    }

    @DeleteMapping("/{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> deleteDocument(
            @AuthenticationPrincipal User authenticatedUser,
            @PathVariable UUID documentId) throws Exception {

        userDocumentService.deleteDocument(documentId, authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }
}