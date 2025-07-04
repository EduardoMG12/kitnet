package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.PropertyImage;
import com.kitnet.kitnet.model.Property; // Para a relação
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, UUID> {
    List<PropertyImage> findByPropertyOrderByOrderIndexAsc(Property property);
    Optional<PropertyImage> findByPropertyAndIsMainImageTrue(Property property);
    void deleteByProperty(Property property); // cascade exclusion
}