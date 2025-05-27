package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.UserDocumentUploadDTO; // Você precisará criar este DTO
import com.kitnet.kitnet.model.UserDocument;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.VerificationStatus;

import java.util.List;
import java.util.UUID;

public interface UserDocumentService {
    UserDocument uploadDocument(UUID userId, UserDocumentUploadDTO dto); // Para upload
    List<UserDocument> getDocumentsByUser(UUID userId);
    UserDocument getDocumentById(UUID documentId);
    UserDocument setDocumentVerificationStatus(UUID documentId, VerificationStatus status, String rejectionReason, User currentUser); // Para Admin/Moderador
    List<UserDocument> findDocumentsNeedingReview(); // Para Admin/Moderador
}