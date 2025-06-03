package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LegalDocumentRepository extends JpaRepository<LegalDocument, UUID> {
    List<LegalDocument> findByIsActiveTrue();
    Optional<LegalDocument> findByTypeAndIsActiveTrue(LegalDocumentType type);
    Optional<LegalDocument> findByTypeAndVersion(LegalDocumentType type, String version);
    boolean existsByTypeAndVersion(LegalDocumentType type, String version); // <-- Adicione isso
    List<LegalDocument> findAllByTypeAndIsActiveTrue(LegalDocumentType type);
}