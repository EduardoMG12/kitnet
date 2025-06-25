package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.UserDocumentVersion;
import com.kitnet.kitnet.model.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDocumentVersionRepository extends JpaRepository<UserDocumentVersion, UUID> {
    List<UserDocumentVersion> findByUserDocumentOrderByUploadDateDesc(UserDocument userDocument);
    Optional<UserDocumentVersion> findByUserDocumentAndIsCurrentVersionTrue(UserDocument userDocument);

}