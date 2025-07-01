package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH u.userLegalDocuments uld LEFT JOIN FETCH uld.legalDocument ld WHERE u.id = :id")
    Optional<User> findByIdWithCollections(@Param("id") UUID id);
}