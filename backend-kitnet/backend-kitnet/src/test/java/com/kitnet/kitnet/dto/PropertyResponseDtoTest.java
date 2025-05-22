package com.kitnet.kitnet.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PropertyResponseDtoTest {

    @Test
    void testNoArgsConstructor() {
        PropertyResponseDto dto = new PropertyResponseDto();
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getAdTitle());
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        String propertyType = "House";
        String adTitle = "Beautiful House for Sale";
        String description = "Spacious house with a garden.";
        String purpose = "Sale";
        Double rentValue = 500000.0;
        String zipCode = "12345-678";
        String state = "RJ";
        String city = "Rio de Janeiro";
        String neighborhood = "Copacabana";
        String address = "Rua Teste";
        String number = "200";
        String complement = "House 1";
        Boolean hideExactAddress = true;
        Double squareMeters = 200.0;
        Double builtArea = 180.0;
        Integer bedrooms = 4;
        Integer bathrooms = 3;
        Integer parkingSpaces = 2;
        String amenities = "Pool, BBQ";
        Integer floor = 0;
        Double condominiumFee = 0.0;
        String photos = "photo1.jpg";
        Boolean ownerConfirmation = true;
        Boolean termsAgreement = true;
        String ownerEmail = "owner@test.com";

        PropertyResponseDto dto = new PropertyResponseDto(
                id, propertyType, adTitle, description, purpose, rentValue, zipCode, state, city,
                neighborhood, address, number, complement, hideExactAddress, squareMeters,
                builtArea, bedrooms, bathrooms, parkingSpaces, amenities, floor,
                condominiumFee, photos, ownerConfirmation, termsAgreement, ownerEmail
        );

        assertEquals(id, dto.getId());
        assertEquals(propertyType, dto.getPropertyType());
        assertEquals(adTitle, dto.getAdTitle());
        assertEquals(description, dto.getDescription());
        assertEquals(purpose, dto.getPurpose());
        assertEquals(rentValue, dto.getRentValue());
        assertEquals(zipCode, dto.getZipCode());
        assertEquals(state, dto.getState());
        assertEquals(city, dto.getCity());
        assertEquals(neighborhood, dto.getNeighborhood());
        assertEquals(address, dto.getAddress());
        assertEquals(number, dto.getNumber());
        assertEquals(complement, dto.getComplement());
        assertEquals(hideExactAddress, dto.getHideExactAddress());
        assertEquals(squareMeters, dto.getSquareMeters());
        assertEquals(builtArea, dto.getBuiltArea());
        assertEquals(bedrooms, dto.getBedrooms());
        assertEquals(bathrooms, dto.getBathrooms());
        assertEquals(parkingSpaces, dto.getParkingSpaces());
        assertEquals(amenities, dto.getAmenities());
        assertEquals(floor, dto.getFloor());
        assertEquals(condominiumFee, dto.getCondominiumFee());
        assertEquals(photos, dto.getPhotos());
        assertEquals(ownerConfirmation, dto.getOwnerConfirmation());
        assertEquals(termsAgreement, dto.getTermsAgreement());
        assertEquals(ownerEmail, dto.getOwnerEmail());
    }

    @Test
    void testSetters() {
        PropertyResponseDto dto = new PropertyResponseDto();

        UUID id = UUID.randomUUID();
        dto.setId(id);
        assertEquals(id, dto.getId());

        dto.setAdTitle("Updated Ad Title");
        assertEquals("Updated Ad Title", dto.getAdTitle());

        dto.setRentValue(1800.0);
        assertEquals(1800.0, dto.getRentValue());

        dto.setOwnerEmail("new@test.com");
        assertEquals("new@test.com", dto.getOwnerEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        PropertyResponseDto dto1 = new PropertyResponseDto();
        dto1.setId(id);
        dto1.setAdTitle("Test Ad");

        PropertyResponseDto dto2 = new PropertyResponseDto();
        dto2.setId(id);
        dto2.setAdTitle("Test Ad");

        PropertyResponseDto dto3 = new PropertyResponseDto();
        dto3.setId(UUID.randomUUID());
        dto3.setAdTitle("Another Ad");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertNotEquals(dto1, dto3);
    }
}