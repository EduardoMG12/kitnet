package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.UserDocument;
import com.kitnet.kitnet.model.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {
    List<UserDocument> findByUserId(UUID userId);
    Optional<UserDocument> findByUserIdAndDocumentType(UUID userId, DocumentType documentType);
    @Query("SELECT ud FROM UserDocument ud LEFT JOIN FETCH ud.versions udv WHERE ud.id = :id")
    Optional<UserDocument> findByIdWithVersions(@Param("id") UUID id);
}