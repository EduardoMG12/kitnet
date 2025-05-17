package com.kitnet.kitnet.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PropertyRequestDto {
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
}
