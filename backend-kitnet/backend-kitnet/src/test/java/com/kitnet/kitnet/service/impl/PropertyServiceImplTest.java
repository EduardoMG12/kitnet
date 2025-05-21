package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    private PropertyRequestDto sampleRequestDto;
    private Property sampleProperty;
    private PropertyResponseDto sampleResponseDto;

    @BeforeEach
    void setUp() {
        sampleRequestDto = new PropertyRequestDto();
        sampleRequestDto.setAdTitle("Test Kitnet");
        sampleRequestDto.setCity("Sample City");
        sampleRequestDto.setState("SS");
        sampleRequestDto.setPurpose("Rent");
        sampleRequestDto.setRentValue(1000.0);
        sampleRequestDto.setPropertyType("Kitnet");
        sampleRequestDto.setOwnerConfirmation(true);
        sampleRequestDto.setTermsAgreement(true);
        sampleRequestDto.setDescription("A test description.");
        sampleRequestDto.setZipCode("12345-000");
        sampleRequestDto.setNeighborhood("Sample Neighborhood");
        sampleRequestDto.setAddress("Sample Address");
        sampleRequestDto.setNumber("123");
        sampleRequestDto.setComplement("Apt 1");
        sampleRequestDto.setHideExactAddress(false);
        sampleRequestDto.setSquareMeters(30.0);
        sampleRequestDto.setBuiltArea(28.0);
        sampleRequestDto.setBedrooms(1);
        sampleRequestDto.setBathrooms(1);
        sampleRequestDto.setParkingSpaces(0);
        sampleRequestDto.setAmenities("Wi-Fi, Furniture");
        sampleRequestDto.setFloor(1);
        sampleRequestDto.setCondominiumFee(150.0);
        sampleRequestDto.setPhotos("photo1.jpg");


        sampleProperty = new Property();
        sampleProperty.setId(1L);
        sampleProperty.setAdTitle("Test Kitnet");
        sampleProperty.setCity("Sample City");
        sampleProperty.setState("SS");
        sampleProperty.setPurpose("Rent");
        sampleProperty.setRentValue(1000.0);
        sampleProperty.setPropertyType("Kitnet");
        sampleProperty.setOwnerConfirmation(true);
        sampleProperty.setTermsAgreement(true);
        sampleProperty.setDescription("A test description.");
        sampleProperty.setZipCode("12345-000");
        sampleProperty.setNeighborhood("Sample Neighborhood");
        sampleProperty.setAddress("Sample Address");
        sampleProperty.setNumber("123");
        sampleProperty.setComplement("Apt 1");
        sampleProperty.setHideExactAddress(false);
        sampleProperty.setSquareMeters(30.0);
        sampleProperty.setBuiltArea(28.0);
        sampleProperty.setBedrooms(1);
        sampleProperty.setBathrooms(1);
        sampleProperty.setParkingSpaces(0);
        sampleProperty.setAmenities("Wi-Fi, Furniture");
        sampleProperty.setFloor(1);
        sampleProperty.setCondominiumFee(150.0);
        sampleProperty.setPhotos("photo1.jpg");


        sampleResponseDto = new PropertyResponseDto();
        sampleResponseDto.setId(1L);
        sampleResponseDto.setAdTitle("Test Kitnet");
        sampleResponseDto.setCity("Sample City");
        sampleResponseDto.setState("SS");
        sampleResponseDto.setPurpose("Rent");
        sampleResponseDto.setRentValue(1000.0);
        sampleResponseDto.setPropertyType("Kitnet");
        sampleResponseDto.setOwnerConfirmation(true);
        sampleResponseDto.setDescription("A test description.");
    }

    @Test
    void testCreateProperty() {
        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);

        PropertyResponseDto result = propertyService.create(sampleRequestDto);

        assertNotNull(result);
        assertEquals(sampleProperty.getId(), result.getId());
        assertEquals(sampleProperty.getAdTitle(), result.getAdTitle());


        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testFindAllProperties() {

        when(propertyRepository.findAll()).thenReturn(Arrays.asList(sampleProperty));

        List<PropertyResponseDto> results = propertyService.findAll();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(sampleProperty.getAdTitle(), results.get(0).getAdTitle());

        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void testFindPropertyByIdFound() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(sampleProperty));

        PropertyResponseDto result = propertyService.findById(1L);

        assertNotNull(result);
        assertEquals(sampleProperty.getId(), result.getId());
        assertEquals(sampleProperty.getAdTitle(), result.getAdTitle());

        verify(propertyRepository, times(1)).findById(1L);
    }

    @Test
    void testFindPropertyByIdNotFound() {
        when(propertyRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            propertyService.findById(99L);
        });

        assertEquals("Property not found", thrown.getMessage());
        verify(propertyRepository, times(1)).findById(99L);
    }

    @Test
    void testUpdateProperty() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(sampleProperty));

        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);

        PropertyResponseDto result = propertyService.update(1L, sampleRequestDto);

        assertNotNull(result);
        assertEquals(sampleProperty.getId(), result.getId());
        assertEquals(sampleRequestDto.getAdTitle(), result.getAdTitle());

        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testUpdatePropertyNotFound() {
        when(propertyRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            propertyService.update(99L, sampleRequestDto);
        });

        assertEquals("Property not found", thrown.getMessage());
        verify(propertyRepository, times(1)).findById(99L);
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testDeleteProperty() {

        doNothing().when(propertyRepository).deleteById(1L);

        propertyService.delete(1L);

        verify(propertyRepository, times(1)).deleteById(1L);
    }
}
