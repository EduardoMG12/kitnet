package com.kitnet.kitnet.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    @Test
    void testNoArgsConstructor() {
        Property property = new Property();
        assertNotNull(property);
        assertNull(property.getId());
        assertNull(property.getAdTitle());
        assertNull(property.getRentValue());
        assertNull(property.getOwner());
    }

    @Test
    void testSettersAndGetters() {
        Property property = new Property();
        UUID id = UUID.randomUUID();
        User owner = new User();
        owner.setId(UUID.randomUUID());
        String propertyType = "Apartment";
        String adTitle = "Beautiful Apartment";
        String description = "A cozy place in the city center.";
        String purpose = "Rent";
        Double rentValue = 1500.0;
        String zipCode = "12345-678";
        String state = "SP";
        String city = "Sao Paulo";
        String neighborhood = "Pinheiros";
        String address = "Rua das Flores";
        String number = "100";
        String complement = "Apt 5";
        Boolean hideExactAddress = false;
        Double squareMeters = 70.0;
        Double builtArea = 65.0;
        Integer bedrooms = 2;
        Integer bathrooms = 1;
        Integer parkingSpaces = 1;
        String amenities = "Pool, Gym";
        Integer floor = 3;
        Double condominiumFee = 300.0;
        String photos = "photo1.jpg,photo2.jpg";
        Boolean ownerConfirmation = true;
        Boolean termsAgreement = true;

        property.setId(id);
        property.setOwner(owner);
        property.setPropertyType(propertyType);
        property.setAdTitle(adTitle);
        property.setDescription(description);
        property.setPurpose(purpose);
        property.setRentValue(rentValue);
        property.setZipCode(zipCode);
        property.setState(state);
        property.setCity(city);
        property.setNeighborhood(neighborhood);
        property.setAddress(address);
        property.setNumber(number);
        property.setComplement(complement);
        property.setHideExactAddress(hideExactAddress);
        property.setSquareMeters(squareMeters);
        property.setBuiltArea(builtArea);
        property.setBedrooms(bedrooms);
        property.setBathrooms(bathrooms);
        property.setParkingSpaces(parkingSpaces);
        property.setAmenities(amenities);
        property.setFloor(floor);
        property.setCondominiumFee(condominiumFee);
        property.setPhotos(photos);
        property.setOwnerConfirmation(ownerConfirmation);
        property.setTermsAgreement(termsAgreement);

        assertEquals(id, property.getId());
        assertEquals(owner, property.getOwner());
        assertEquals(propertyType, property.getPropertyType());
        assertEquals(adTitle, property.getAdTitle());
        assertEquals(description, property.getDescription());
        assertEquals(purpose, property.getPurpose());
        assertEquals(rentValue, property.getRentValue());
        assertEquals(zipCode, property.getZipCode());
        assertEquals(state, property.getState());
        assertEquals(city, property.getCity());
        assertEquals(neighborhood, property.getNeighborhood());
        assertEquals(address, property.getAddress());
        assertEquals(number, property.getNumber());
        assertEquals(complement, property.getComplement());
        assertEquals(hideExactAddress, property.getHideExactAddress());
        assertEquals(squareMeters, property.getSquareMeters());
        assertEquals(builtArea, property.getBuiltArea());
        assertEquals(bedrooms, property.getBedrooms());
        assertEquals(bathrooms, property.getBathrooms());
        assertEquals(parkingSpaces, property.getParkingSpaces());
        assertEquals(amenities, property.getAmenities());
        assertEquals(floor, property.getFloor());
        assertEquals(condominiumFee, property.getCondominiumFee());
        assertEquals(photos, property.getPhotos());
        assertEquals(ownerConfirmation, property.getOwnerConfirmation());
        assertEquals(termsAgreement, property.getTermsAgreement());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        User owner = new User();
        owner.setId(UUID.randomUUID());

        Property property1 = new Property();
        property1.setId(id);
        property1.setOwner(owner);
        property1.setAdTitle("Test Ad");
        property1.setPropertyType("House");

        Property property2 = new Property();
        property2.setId(id);
        property2.setOwner(owner);
        property2.setAdTitle("Test Ad");
        property2.setPropertyType("House");

        Property property3 = new Property();
        property3.setId(UUID.randomUUID());
        property3.setOwner(new User());
        property3.setAdTitle("Another Ad");
        property3.setPropertyType("Apartment");

        assertEquals(property1, property2);
        assertEquals(property1.hashCode(), property2.hashCode());

        assertNotEquals(property1, property3);
    }

    @Test
    void testToString() {
        Property property = new Property();
        UUID id = UUID.randomUUID();
        property.setId(id);
        property.setAdTitle("Test Ad");
        property.setPropertyType("House");

        String toStringResult = property.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("id=" + id));
        assertTrue(toStringResult.contains("adTitle=Test Ad"));
        assertTrue(toStringResult.contains("propertyType=House"));
    }
}