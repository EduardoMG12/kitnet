package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {
    List<Property> findByOwner(User owner);
    List<Property> findByAgent(User agent);
    List<Property> findByIsAvailableTrue();

    @Query("SELECT p FROM Property p LEFT JOIN FETCH p.owner LEFT JOIN FETCH p.agent WHERE p.isAvailable = true")
    List<Property> findAllAvailableWithUsers();

    @Query("SELECT p FROM Property p LEFT JOIN FETCH p.owner LEFT JOIN FETCH p.agent WHERE p.id = :id")
    Optional<Property> findByIdWithUsers(@Param("id") UUID id);
}