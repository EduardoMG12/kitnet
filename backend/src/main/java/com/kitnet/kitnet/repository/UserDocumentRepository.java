package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.UserDocument;
import com.kitnet.kitnet.model.enums.DocumentType;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {
    List<UserDocument> findByUserId(UUID userId);
    Optional<UserDocument> findByUserIdAndDocumentType(UUID userId, DocumentType documentType);
}