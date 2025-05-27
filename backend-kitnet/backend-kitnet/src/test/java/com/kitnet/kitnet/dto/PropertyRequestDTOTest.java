package com.kitnet.kitnet.dto;

import com.kitnet.kitnet.dto.property.PropertyRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PropertyRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        PropertyRequestDTO dto = new PropertyRequestDTO();
        assertNotNull(dto);
        assertNull(dto.getOwnerId());
        assertNull(dto.getAdTitle());
        assertNull(dto.getRentValue());
        assertNull(dto.getOwnerConfirmation());
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        UUID ownerId = UUID.randomUUID();
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

        PropertyRequestDTO dto = new PropertyRequestDTO(
                ownerId, propertyType, adTitle, description, purpose, rentValue, zipCode, state, city,
                neighborhood, address, number, complement, hideExactAddress, squareMeters,
                builtArea, bedrooms, bathrooms, parkingSpaces, amenities, floor,
                condominiumFee, photos, ownerConfirmation, termsAgreement
        );

        assertEquals(ownerId, dto.getOwnerId());
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
        PropertyRequestDTO dto = new PropertyRequestDTO();

        UUID ownerId = UUID.randomUUID();
        dto.setOwnerId(ownerId);
        assertEquals(ownerId, dto.getOwnerId());

        dto.setAdTitle("New Ad Title");
        assertEquals("New Ad Title", dto.getAdTitle());

        dto.setRentValue(2000.0);
        assertEquals(2000.0, dto.getRentValue());

        dto.setOwnerConfirmation(false);
        assertFalse(dto.getOwnerConfirmation());
    }

    @Test
    void testValidationConstraints() {
        PropertyRequestDTO dto = new PropertyRequestDTO();
        Set<ConstraintViolation<PropertyRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size()); // @NotBlank para propertyType, adTitle, purpose; @NotNull para ownerConfirmation, termsAgreement

        violations.forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            switch (propertyPath) {
                case "propertyType":
                    assertEquals("O tipo de propriedade não pode estar em branco", message);
                    break;
                case "adTitle":
                    assertEquals("O título do anúncio não pode estar em branco", message);
                    break;
                case "purpose":
                    assertEquals("A finalidade não pode estar em branco", message);
                    break;
                case "ownerConfirmation":
                    assertEquals("A confirmação de proprietário é obrigatória", message);
                    break;
                case "termsAgreement":
                    assertEquals("A aceitação dos termos é obrigatória", message);
                    break;
            }
        });
    }

    @Test
    void testEqualsAndHashCode() {
        UUID ownerId = UUID.randomUUID();
        PropertyRequestDTO dto1 = new PropertyRequestDTO();
        dto1.setOwnerId(ownerId);
        dto1.setAdTitle("Test Ad");
        dto1.setRentValue(1000.0);

        PropertyRequestDTO dto2 = new PropertyRequestDTO();
        dto2.setOwnerId(ownerId);
        dto2.setAdTitle("Test Ad");
        dto2.setRentValue(1000.0);

        PropertyRequestDTO dto3 = new PropertyRequestDTO();
        dto3.setOwnerId(UUID.randomUUID());
        dto3.setAdTitle("Another Ad");
        dto3.setRentValue(2000.0);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertNotEquals(dto1, dto3);
    }
}