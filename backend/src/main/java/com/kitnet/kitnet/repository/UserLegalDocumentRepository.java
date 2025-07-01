package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.UserLegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserLegalDocumentRepository extends JpaRepository<UserLegalDocument, UUID> {
    Optional<UserLegalDocument> findByUserAndType(User user, LegalDocumentType type);
    Optional<UserLegalDocument> findByUserAndTypeAndIsCurrentOfUserTrue(User user, LegalDocumentType type);
}