package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.EmploymentHistory;
import com.kitnet.kitnet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmploymentHistoryRepository extends JpaRepository<EmploymentHistory, UUID> {
    List<EmploymentHistory> findByUser(User user);
    Optional<EmploymentHistory> findByIdAndUser(UUID id, User user);
}
