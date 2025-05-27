package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.UserDocument;
import com.kitnet.kitnet.model.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {
    List<UserDocument> findByUserId(UUID userId);
    List<UserDocument> findByUserIdAndDocumentType(UUID userId, String documentType);
    List<UserDocument> findByVerificationStatus(VerificationStatus status);
}