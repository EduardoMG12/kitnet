package com.kitnet.kitnet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponseDto {
    private UUID id; // Alterado de Long para UUID
    private String propertyType;
    private String adTitle;
    private String description;
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
    private String amenities;
    private Integer floor;
    private Double condominiumFee;
    private String photos;
    private Boolean ownerConfirmation;
    private Boolean termsAgreement;
    private String ownerEmail;
}