package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLegalDocument(String legalDocument);
    Optional<User> findById(UUID id);
    Optional<User> findByFirebaseUid(String firebaseUid);
    List<User> findByAccountVerificationStatus(VerificationStatus status);
}