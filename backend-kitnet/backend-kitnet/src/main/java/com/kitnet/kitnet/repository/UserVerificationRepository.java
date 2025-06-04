package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.EmailVerificationToken;
import com.kitnet.kitnet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserVerificationRepository extends JpaRepository<EmailVerificationToken, UUID> {
    Optional<EmailVerificationToken> findByTokenAndUsedFalse(String token);
    Optional<EmailVerificationToken> findByUserAndUsedFalseAndExpiryDateAfter(User user, LocalDateTime now);
}