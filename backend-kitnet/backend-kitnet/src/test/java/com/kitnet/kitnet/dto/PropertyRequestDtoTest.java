package com.kitnet.kitnet.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PropertyRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        // Testa o construtor sem argumentos e se os campos são inicializados para null/default
        PropertyRequestDto dto = new PropertyRequestDto();
        assertNotNull(dto); // Garante que o objeto foi criado
        assertNull(dto.getAdTitle());
        assertNull(dto.getRentValue());
        assertNull(dto.getOwnerConfirmation());
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        // Testa o construtor com todos os argumentos e os getters
        String propertyType = "Apartment";
        String adTitle = "Cozy Apartment for Rent";
        String description = "A beautiful apartment in the city center.";
        String purpose = "Rent";
        Double rentValue = 1500.0;
        String zipCode = "12345-678";
        String state = "SP";
        String city = "Sao Paulo";
        String neighborhood = "Jardins";
        String address = "Rua Teste";
        String number = "100";
        String complement = "Apt 5";
        Boolean hideExactAddress = false;
        Double squareMeters = 75.0;
        Double builtArea = 70.0;
        Integer bedrooms = 2;
        Integer bathrooms = 1;
        Integer parkingSpaces = 1;
        String amenities = "Pool, Gym";
        Integer floor = 5;
        Double condominiumFee = 300.0;
        String photos = "photo1.jpg,photo2.jpg";
        Boolean ownerConfirmation = true;
        Boolean termsAgreement = true;

        PropertyRequestDto dto = new PropertyRequestDto(
                propertyType, adTitle, description, purpose, rentValue, zipCode, state, city,
                neighborhood, address, number, complement, hideExactAddress, squareMeters,
                builtArea, bedrooms, bathrooms, parkingSpaces, amenities, floor,
                condominiumFee, photos, ownerConfirmation, termsAgreement
        );

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
    }

    @Test
    void testSetters() {
        // Testa os setters
        PropertyRequestDto dto = new PropertyRequestDto();

        dto.setAdTitle("New Ad Title");
        assertEquals("New Ad Title", dto.getAdTitle());

        dto.setRentValue(2000.0);
        assertEquals(2000.0, dto.getRentValue());

        dto.setOwnerConfirmation(false);
        assertFalse(dto.getOwnerConfirmation());
    }

    @Test
    void testEqualsAndHashCode() {
        // Testa os métodos equals e hashCode gerados pelo Lombok
        PropertyRequestDto dto1 = new PropertyRequestDto();
        dto1.setAdTitle("Test Ad");
        dto1.setRentValue(1000.0);

        PropertyRequestDto dto2 = new PropertyRequestDto();
        dto2.setAdTitle("Test Ad");
        dto2.setRentValue(1000.0);

        PropertyRequestDto dto3 = new PropertyRequestDto();
        dto3.setAdTitle("Another Ad");
        dto3.setRentValue(2000.0);

        // Objetos com os mesmos valores devem ser iguais e ter o mesmo hashCode
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Objetos com valores diferentes não devem ser iguais
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode()); // Hash codes podem colidir, mas geralmente não para objetos diferentes
    }
}
