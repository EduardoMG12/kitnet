package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.PropertyListing;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.ListingModerationStatus;
import com.kitnet.kitnet.model.enums.ListingVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyListingRepository extends JpaRepository<PropertyListing, UUID> {
    List<PropertyListing> findByProperty(Property property);
    List<PropertyListing> findByLister(User lister);
    List<PropertyListing> findByModerationStatus(ListingModerationStatus status);
    List<PropertyListing> findByIsActiveTrueAndVisibility(ListingVisibility visibility);

    @Query("SELECT pl FROM PropertyListing pl LEFT JOIN FETCH pl.property p LEFT JOIN FETCH pl.lister l LEFT JOIN FETCH pl.template pt WHERE pl.isActive = true AND pl.visibility = 'PUBLIC'")
    List<PropertyListing> findAllPublicAndActiveListingsWithDetails();

    @Query("SELECT pl FROM PropertyListing pl LEFT JOIN FETCH pl.property p LEFT JOIN FETCH pl.lister l LEFT JOIN FETCH pl.template pt WHERE pl.id = :id")
    Optional<PropertyListing> findByIdWithDetails(@Param("id") UUID id);
}