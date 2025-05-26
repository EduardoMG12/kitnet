package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.Rental;
import com.kitnet.kitnet.model.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID; // Importar UUID

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {
    @Query("SELECT r FROM Rental r WHERE r.property.id = :propertyId " +
            "AND r.status IN :activeStatuses " +
            "AND (" +
            "   (:newStartDate BETWEEN r.startDate AND r.endDate) OR " +
            "   (:newEndDate BETWEEN r.startDate AND r.endDate) OR " +
            "   (r.startDate BETWEEN :newStartDate AND :newEndDate) OR " +
            "   (r.endDate BETWEEN :newStartDate AND :newEndDate)" +
            ")")
    List<Rental> findConflictingRentals(
            @Param("propertyId") UUID propertyId,
            @Param("newStartDate") LocalDate newStartDate,
            @Param("newEndDate") LocalDate newEndDate,
            @Param("activeStatuses") List<RentalStatus> activeStatuses);

    List<Rental> findByTenantId(UUID tenantId);
    List<Rental> findByPropertyId(UUID propertyId);
}