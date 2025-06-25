package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.exception.UserNotFoundException;
import com.kitnet.kitnet.exception.FileUploadException;
import com.kitnet.kitnet.exception.InvalidFileFormatException;
import com.kitnet.kitnet.exception.FileSizeExceededException;
import com.kitnet.kitnet.exception.InvalidOperationException;
import com.kitnet.kitnet.exception.DocumentNotFoundException;
import com.kitnet.kitnet.exception.UnauthorizedOperationException;
import com.kitnet.kitnet.exception.DocumentValidationException;

import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.UserDocument;
import com.kitnet.kitnet.model.UserDocumentVersion;
import com.kitnet.kitnet.model.enums.DocumentType;
import com.kitnet.kitnet.model.enums.LegalPersonType;
import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import com.kitnet.kitnet.repository.UserDocumentRepository;
import com.kitnet.kitnet.repository.UserDocumentVersionRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.UserDocumentService;
import com.kitnet.kitnet.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDocumentServiceImpl implements UserDocumentService {

    @Autowired
    private UserDocumentRepository userDocumentRepository;
    @Autowired
    private UserDocumentVersionRepository userDocumentVersionRepository; // Injetar o repositório de versões
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public List<UserDocument> uploadMultipleVerificationDocuments(UUID userId, List<MultipartFile> files, List<DocumentType> documentTypes, UUID authenticatedUserId)
            throws UserNotFoundException, IOException, FileUploadException, InvalidFileFormatException, FileSizeExceededException, InvalidOperationException, DocumentValidationException, UnauthorizedOperationException {

        Locale locale = LocaleContextHolder.getLocale();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.not.found", null, locale)));
        User actingUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        if (!userId.equals(authenticatedUserId) && !actingUser.hasRole(RoleName.ADMIN) && !actingUser.hasRole(RoleName.MODERATOR)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.document.upload.unauthorized", null, locale));
        }

        if (files.size() != documentTypes.size()) {
            throw new InvalidOperationException(messageSource.getMessage("error.document.upload.mismatch", null, locale));
        }
        if (files.isEmpty()) {
            throw new FileUploadException(messageSource.getMessage("error.file.empty", null, locale));
        }

        List<UserDocument> processedDocuments = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            DocumentType documentType = documentTypes.get(i);

            validateDocumentTypeAndContent(file, documentType, user, locale);

            UserDocument userDocument = userDocumentRepository.findByUserIdAndDocumentType(user.getId(), documentType)
                    .orElseGet(() -> {
                        UserDocument newDoc = new UserDocument();
                        newDoc.setUser(user);
                        newDoc.setDocumentType(documentType);
                        return userDocumentRepository.save(newDoc);
                    });

            userDocument.getVersions().stream()
                    .filter(UserDocumentVersion::isCurrentVersion)
                    .findFirst()
                    .ifPresent(oldVersion -> {
                        oldVersion.setCurrentVersion(false);
                        userDocumentVersionRepository.save(oldVersion);
                    });

            String subdirectory = "users/" + userId.toString() + "/documents/" + documentType.name().toLowerCase();
            String documentUrl = uploadService.uploadFile(file, subdirectory);

            UserDocumentVersion newVersion = new UserDocumentVersion();
            newVersion.setUserDocument(userDocument);
            newVersion.setDocumentUrl(documentUrl);
            newVersion.setUploadDate(LocalDate.now());
            newVersion.setVerificationStatus(VerificationStatus.NOT_SUBMITTED);
            newVersion.setRejectionReason(null);
            newVersion.setCurrentVersion(true);

            userDocumentVersionRepository.save(newVersion);
            userDocument.getVersions().add(newVersion);

            processedDocuments.add(userDocument);
        }

        boolean updateNeeded = processedDocuments.stream().anyMatch(doc -> isCriticalDocumentType(doc.getDocumentType())) ||
                user.getAccountVerificationStatus() == VerificationStatus.REJECTED ||
                user.getAccountVerificationStatus() == VerificationStatus.NOT_SUBMITTED;

        if (updateNeeded) {
            user.setAccountVerificationStatus(VerificationStatus.PENDING);
            user.setIsIdentityConfirmed(false);
            userRepository.save(user);
        }

        return processedDocuments;
    }

    private void validateDocumentTypeAndContent(MultipartFile file, DocumentType documentType, User user, Locale locale) {
        String contentType = Objects.requireNonNull(file.getContentType());
        long fileSize = file.getSize();

        final long MAX_DOC_SIZE_MB = 10;
        if (fileSize > MAX_DOC_SIZE_MB * 1024 * 1024) {
            throw new FileSizeExceededException(messageSource.getMessage("error.file.size.exceeded", new Object[]{MAX_DOC_SIZE_MB}, locale));
        }

        if (!contentType.startsWith("image/") && !contentType.equals("application/pdf")) {
            throw new InvalidFileFormatException(messageSource.getMessage("error.file.format.invalid", new Object[]{"image/jpeg, image/png, application/pdf"}, locale));
        }

        switch (documentType) {
            case CPF:
                if (user.getLegalPersonType() != LegalPersonType.PF) {
                    throw new DocumentValidationException(messageSource.getMessage("error.document.type.mismatch", new Object[]{"CPF", "Pessoa Física"}, locale));
                }
                break;
            case CNPJ:
                if (user.getLegalPersonType() != LegalPersonType.PJ) {
                    throw new DocumentValidationException(messageSource.getMessage("error.document.type.mismatch", new Object[]{"CNPJ", "Pessoa Jurídica"}, locale));
                }
                break;
            case CRECI:
                if (user.getLegalPersonType() != LegalPersonType.PF || !user.hasRole(RoleName.REAL_ESTATE_AGENT)) {
                    throw new DocumentValidationException(messageSource.getMessage("error.document.creci.role.mismatch", null, locale));
                }
                break;
            case RG_FRENTE:
            case RG_VERSO:
            case CNH_FRENTE:
            case CNH_VERSO:
            case SELFIE_COM_DOCUMENTO:
                if (user.getLegalPersonType() != LegalPersonType.PF) {
                    throw new DocumentValidationException(messageSource.getMessage("error.document.type.mismatch", new Object[]{documentType.toString(), "Pessoa Física"}, locale));
                }
                break;
            // Add specific checks for PROVA_PROPRIEDADE_IMOVEL, PROCURACAO based on context
        }
    }

    private boolean isCriticalDocumentType(DocumentType documentType) {
        return Set.of(
                DocumentType.CPF,
                DocumentType.CNPJ,
                DocumentType.RG_FRENTE,
                DocumentType.CNH_FRENTE,
                DocumentType.SELFIE_COM_DOCUMENTO,
                DocumentType.COMPROVANTE_RESIDENCIA,
                DocumentType.CRECI,
                DocumentType.PROVA_PROPRIEDADE_IMOVEL,
                DocumentType.CONTRATO_SOCIAL
        ).contains(documentType);
    }

    private boolean checkAllCriticalDocumentsApproved(User user) {
        List<UserDocument> userDocsHeads = userDocumentRepository.findByUserId(user.getId());

        List<UserDocumentVersion> currentApprovedDocs = userDocsHeads.stream()
                .map(UserDocument::getCurrentVersion)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(v -> v.getVerificationStatus() == VerificationStatus.APPROVED)
                .collect(Collectors.toList());

        Set<DocumentType> approvedDocTypes = currentApprovedDocs.stream()
                .map(v -> v.getUserDocument().getDocumentType())
                .collect(Collectors.toSet());

        Set<DocumentType> requiredCriticalTypes = new HashSet<>();

        if (user.getLegalPersonType() == LegalPersonType.PF) {
            requiredCriticalTypes.add(DocumentType.CPF);
            requiredCriticalTypes.add(DocumentType.COMPROVANTE_RESIDENCIA);
            requiredCriticalTypes.add(DocumentType.SELFIE_COM_DOCUMENTO);
            // For RG/CNH: at least one of RG_FRENTE or CNH_FRENTE must be approved
            boolean hasApprovedRgOrCnh = approvedDocTypes.contains(DocumentType.RG_FRENTE) ||
                    approvedDocTypes.contains(DocumentType.CNH_FRENTE);
            if (!hasApprovedRgOrCnh) return false;
        } else if (user.getLegalPersonType() == LegalPersonType.PJ) {
            requiredCriticalTypes.add(DocumentType.CNPJ);
            requiredCriticalTypes.add(DocumentType.CONTRATO_SOCIAL);
            // COMPROVANTE_INSCRICAO_ESTADUAL might be critical depending on business rule
            // requiredCriticalTypes.add(DocumentType.COMPROVANTE_INSCRICAO_ESTADUAL);
            requiredCriticalTypes.add(DocumentType.COMPROVANTE_RESIDENCIA); // For PJ too
        }

        // Add role-specific critical documents
        if (user.hasRole(RoleName.REAL_ESTATE_AGENT)) {
            requiredCriticalTypes.add(DocumentType.CRECI);
        }

        // PROVA_PROPRIEDADE_IMOVEL is usually associated with specific properties, not general user verification
        // But if it's considered critical for a LESSOR's *overall account verification*, include it here.
        // For now, let's assume it's part of property verification, not user account verification.
        // if (user.hasRole(RoleName.LESSOR) && user.getLegalPersonType() == LegalPersonType.PF) {
        //     requiredCriticalTypes.add(DocumentType.PROVA_PROPRIEDADE_IMOVEL);
        // }

        // Final check: Are all defined required critical types present in the approved types?
        return approvedDocTypes.containsAll(requiredCriticalTypes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDocument> getUserDocuments(UUID userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(messageSource.getMessage("error.user.not.found", null, LocaleContextHolder.getLocale()));
        }
        return userDocumentRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDocument getDocumentById(UUID documentId) throws DocumentNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        UserDocument document = userDocumentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(messageSource.getMessage("error.document.not.found", null, locale)));
        return document;
    }

    @Override
    @Transactional
    public UserDocument updateDocumentVerificationStatus(UUID documentId, VerificationStatus status, String rejectionReason, UUID actingUserId)
            throws DocumentNotFoundException, UnauthorizedOperationException, UserNotFoundException, InvalidOperationException {

        Locale locale = LocaleContextHolder.getLocale();

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        if (!actingUser.hasRole(RoleName.ADMIN) && !actingUser.hasRole(RoleName.MODERATOR)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.document.upload.unauthorized", null, locale));
        }

        UserDocument document = userDocumentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(messageSource.getMessage("error.document.not.found", null, locale)));

        UserDocumentVersion currentVersion = document.getCurrentVersion()
                .orElseThrow(() -> new InvalidOperationException(messageSource.getMessage("error.document.no.current.version", null, locale))); // Should not happen if upload is managed

        if (status == VerificationStatus.REJECTED && (rejectionReason == null || rejectionReason.trim().isEmpty())) {
            throw new InvalidOperationException(messageSource.getMessage("error.document.rejection.reason.required", null, locale));
        }

        currentVersion.setVerificationStatus(status);
        currentVersion.setRejectionReason(status == VerificationStatus.REJECTED ? rejectionReason : null);

        userDocumentVersionRepository.save(currentVersion); // Save the updated version

        User user = document.getUser();
        boolean allCriticalApproved = checkAllCriticalDocumentsApproved(user);

        if (allCriticalApproved) {
            user.setAccountVerificationStatus(VerificationStatus.APPROVED);
            user.setIsIdentityConfirmed(true);
        } else if (status == VerificationStatus.REJECTED && isCriticalDocumentType(document.getDocumentType())) {
            user.setAccountVerificationStatus(VerificationStatus.REJECTED);
            user.setIsIdentityConfirmed(false);
        } else if (status == VerificationStatus.APPROVED && !allCriticalApproved) {
            user.setAccountVerificationStatus(VerificationStatus.PENDING);
            user.setIsIdentityConfirmed(false);
        }
        userRepository.save(user);

        return document;
    }

    @Override
    @Transactional
    public void deleteDocument(UUID documentId, UUID actingUserId)
            throws DocumentNotFoundException, UnauthorizedOperationException, IOException, FileUploadException, UserNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        if (!actingUser.hasRole(RoleName.ADMIN) && !actingUser.hasRole(RoleName.MODERATOR)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.document.upload.unauthorized", null, locale));
        }

        UserDocument document = userDocumentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(messageSource.getMessage("error.document.not.found", null, locale)));

        for (UserDocumentVersion version : document.getVersions()) {
            try {
                uploadService.deleteFile(version.getDocumentUrl());
            } catch (IOException | FileUploadException e) {
                System.err.println("Failed to delete physical file for document version " + version.getId() + ": " + e.getMessage());
                // Log and continue, attempt to delete DB record anyway.
            }
        }

        userDocumentRepository.delete(document);

        User user = document.getUser();
        boolean allCriticalApprovedAfterDeletion = checkAllCriticalDocumentsApproved(user);
        if (!allCriticalApprovedAfterDeletion && user.getAccountVerificationStatus() == VerificationStatus.APPROVED) {
            user.setAccountVerificationStatus(VerificationStatus.PENDING);
            user.setIsIdentityConfirmed(false);
            userRepository.save(user);
        }
    }

}