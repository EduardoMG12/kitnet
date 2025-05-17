package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    // Exemplos de métodos personalizados:
    // List<Rental> findByTenantId(Long userId);
}
