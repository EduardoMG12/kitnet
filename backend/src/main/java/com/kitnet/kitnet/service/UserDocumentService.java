package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.userDocument.UserDocumentUploadResponseDTO;
import com.kitnet.kitnet.exception.UserNotFoundException;
import com.kitnet.kitnet.exception.FileUploadException;
import com.kitnet.kitnet.exception.InvalidFileFormatException;
import com.kitnet.kitnet.exception.FileSizeExceededException;
import com.kitnet.kitnet.exception.InvalidOperationException;
import com.kitnet.kitnet.exception.DocumentNotFoundException;
import com.kitnet.kitnet.exception.UnauthorizedOperationException;
import com.kitnet.kitnet.exception.DocumentValidationException;
import com.kitnet.kitnet.model.enums.DocumentType;
import com.kitnet.kitnet.model.UserDocument;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserDocumentService {

    UserDocumentUploadResponseDTO uploadVerificationDocuments(UUID userId, MultipartFile file, DocumentType documentType, UUID authenticatedUserId)
            throws UserNotFoundException, IOException, FileUploadException, InvalidFileFormatException, FileSizeExceededException, InvalidOperationException, DocumentValidationException, UnauthorizedOperationException, IOException;
    List<UserDocument> getUserDocuments(UUID userId) throws UserNotFoundException;
    UserDocument getDocumentById(UUID documentId) throws DocumentNotFoundException;
    UserDocument updateDocumentVerificationStatus(UUID documentId, VerificationStatus status, String rejectionReason, UUID actingUserId)
            throws DocumentNotFoundException, UserNotFoundException, UnauthorizedOperationException, InvalidOperationException;
    void deleteDocument(UUID documentId, UUID actingUserId)
            throws DocumentNotFoundException, UnauthorizedOperationException, IOException, FileUploadException, UserNotFoundException;
}