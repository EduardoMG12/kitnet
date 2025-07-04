package com.kitnet.kitnet.mapper;

import com.kitnet.kitnet.dto.property.PropertyResponseDTO;
import com.kitnet.kitnet.dto.property.PropertyRequestDTO;
import com.kitnet.kitnet.dto.property.PropertyImageDTO;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.PropertyImage;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.PropertyAccountVerificationStatus;
import com.kitnet.kitnet.model.enums.AnnouncingUserRole;
import com.kitnet.kitnet.model.enums.PropertyPurpose;
import com.kitnet.kitnet.model.enums.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PropertyMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static Property toProperty(PropertyRequestDTO dto) {
        if (dto == null) return null;
        Property property = new Property();



        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setType(dto.getType());
        property.setPurpose(dto.getPurpose());
        property.setRentalPrice(dto.getRentalPrice());
        property.setSalePrice(dto.getSalePrice());
        property.setShowRentalPrice(dto.getShowRentalPrice());
        property.setShowSalePrice(dto.getShowSalePrice());
        property.setZipCode(dto.getZipCode());
        property.setState(dto.getState());
        property.setCity(dto.getCity());
        property.setNeighborhood(dto.getNeighborhood());
        property.setAddressStreet(dto.getAddressStreet());
        property.setAddressComplement(dto.getAddressComplement());
        property.setHideExactAddress(dto.getHideExactAddress());
        property.setLatitude(dto.getLatitude());
        property.setLongitude(dto.getLongitude());
        property.setSquareMeters(dto.getSquareMeters());
        property.setBuiltArea(dto.getBuiltArea());
        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setParkingSpaces(dto.getParkingSpaces());
        property.setAcceptsPets(dto.getAcceptsPets());
        property.setAmenities(dto.getAmenities());
        property.setFloor(dto.getFloor());
        property.setCondominiumFee(dto.getCondominiumFee());
        property.setIsAvailable(dto.getIsAvailable());
        property.setOwnerConfirmation(dto.getOwnerConfirmation());
        property.setTermsAgreement(dto.getTermsAgreement());
        property.setAllowOtherAgents(dto.getAllowOtherAgents());
        property.setMaxAllowedAgents(dto.getMaxAllowedAgents());
        property.setBrokerageFeePercentage(dto.getBrokerageFeePercentage());

        return property;
    }


    public static PropertyResponseDTO toPropertyResponseDTO(Property property) {
        if (property == null) return null;
        PropertyResponseDTO dto = new PropertyResponseDTO();
        dto.setId(property.getId());
        dto.setOwner(UserMapper.toUserSimpleResponseDTO(property.getOwner()));
        dto.setAgent(UserMapper.toUserSimpleResponseDTO(property.getAgent()));
        dto.setTitle(property.getTitle());
        dto.setDescription(property.getDescription());
        dto.setType(property.getType());
        dto.setPurpose(property.getPurpose());
        dto.setRentalPrice(property.getRentalPrice());
        dto.setSalePrice(property.getSalePrice());
        dto.setShowRentalPrice(property.getShowRentalPrice());
        dto.setShowSalePrice(property.getShowSalePrice());
        dto.setZipCode(property.getZipCode());
        dto.setState(property.getState());
        dto.setCity(property.getCity());
        dto.setNeighborhood(property.getNeighborhood());
        dto.setAddressStreet(property.getAddressStreet());
        dto.setAddressComplement(property.getAddressComplement());
        dto.setHideExactAddress(property.getHideExactAddress());
        dto.setLatitude(property.getLatitude());
        dto.setLongitude(property.getLongitude());
        dto.setSquareMeters(property.getSquareMeters());
        dto.setBuiltArea(property.getBuiltArea());
        dto.setBedrooms(property.getBedrooms());
        dto.setBathrooms(property.getBathrooms());
        dto.setParkingSpaces(property.getParkingSpaces());
        dto.setAcceptsPets(property.getAcceptsPets());
        dto.setAmenities(property.getAmenities());
        dto.setFloor(property.getFloor());
        dto.setCondominiumFee(property.getCondominiumFee());
        dto.setIsAvailable(property.getIsAvailable());
        dto.setOwnerConfirmation(property.getOwnerConfirmation());
        dto.setTermsAgreement(property.getTermsAgreement());
        dto.setAccountVerificationStatus(property.getAccountVerificationStatus());
        dto.setRejectionReason(property.getRejectionReason());
        dto.setAnnouncingUserRole(property.getAnnouncingUserRole());
        dto.setAllowOtherAgents(property.getAllowOtherAgents());
        dto.setMaxAllowedAgents(property.getMaxAllowedAgents());
        dto.setBrokerageFeePercentage(property.getBrokerageFeePercentage());
        dto.setCreatedAt(property.getCreatedAt());
        dto.setUpdatedAt(property.getUpdatedAt());
        dto.setImages(mapPropertyImagesToDTOs(property.getImages()));

        return dto;
    }


    public static List<PropertyImageDTO> mapPropertyImagesToDTOs(Set<PropertyImage> images) {
        if (images == null) {
            return new ArrayList<>();
        }
        return images.stream()
                .map(PropertyMapper::toPropertyImageDTO)
                .collect(Collectors.toList());
    }


    public static List<PropertyResponseDTO> toPropertyResponseDTOList(List<Property> properties) {
        if (properties == null) return new ArrayList<>();
        return properties.stream()
                .map(PropertyMapper::toPropertyResponseDTO)
                .collect(Collectors.toList());
    }


    public static PropertyImageDTO toPropertyImageDTO(PropertyImage propertyImage) {
        if (propertyImage == null) return null;
        PropertyImageDTO dto = new PropertyImageDTO();
        dto.setId(propertyImage.getId());
        dto.setPropertyId(propertyImage.getProperty() != null ? propertyImage.getProperty().getId() : null);
        dto.setImageUrl(propertyImage.getImageUrl());
        dto.setOrderIndex(propertyImage.getOrderIndex());
        dto.setIsMainImage(propertyImage.getIsMainImage());
        return dto;
    }




    public static String mapStringToJsonOrList(List<String> amenities) {
        if (amenities == null || amenities.isEmpty()) {
            return null;
        }
        try {

            return objectMapper.writeValueAsString(amenities);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting amenities to JSON string: " + e.getMessage());
            return String.join(",", amenities);
        }
    }
}