package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID; // Importar UUID

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {
    List<Rental> findByTenantId(UUID tenantId);
    List<Rental> findByPropertyId(UUID propertyId);
}