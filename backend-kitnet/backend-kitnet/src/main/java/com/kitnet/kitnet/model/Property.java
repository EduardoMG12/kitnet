package com.kitnet.kitnet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String propertyType;

    @Column(nullable = false)
    private String adTitle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String purpose;

    private Double rentValue;

    private String zipCode;

    private String state;

    private String city;

    private String neighborhood;

    private String address;

    private String number;

    private String complement;

    private Boolean hideExactAddress;

    private Double squareMeters;

    private Double builtArea;

    private Integer bedrooms;

    private Integer bathrooms;

    private Integer parkingSpaces;

    @Column(columnDefinition = "TEXT")
    private String amenities;

    private Integer floor;

    private Double condominiumFee;

    @Column(columnDefinition = "TEXT")
    private String photos;

    @Column(nullable = false)
    private Boolean ownerConfirmation;

    @Column(nullable = false)
    private Boolean termsAgreement;
}