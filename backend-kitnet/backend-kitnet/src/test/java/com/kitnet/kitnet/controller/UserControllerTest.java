package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.PropertyRequestDTO;
import com.kitnet.kitnet.dto.PropertyResponseDTO;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.repository.PropertyRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.impl.PropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    private PropertyRequestDTO sampleRequestDto;
    private Property sampleProperty;
    private PropertyResponseDTO sampleResponseDto;
    private User sampleUser;
    private UUID userId;
    private UUID propertyId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        propertyId = UUID.randomUUID();

        sampleUser = new User();
        sampleUser.setId(userId);
        sampleUser.setEmail("test@test.com");

        sampleRequestDto = new PropertyRequestDTO();
        sampleRequestDto.setOwnerId(userId);
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
        sampleProperty.setId(propertyId);
        sampleProperty.setOwner(sampleUser);
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

        sampleResponseDto = new PropertyResponseDTO();
        sampleResponseDto.setId(propertyId);
        sampleResponseDto.setAdTitle("Test Kitnet");
        sampleResponseDto.setCity("Sample City");
        sampleResponseDto.setState("SS");
        sampleResponseDto.setPurpose("Rent");
        sampleResponseDto.setRentValue(1000.0);
        sampleResponseDto.setPropertyType("Kitnet");
        sampleResponseDto.setOwnerConfirmation(true);
        sampleResponseDto.setTermsAgreement(true);
        sampleResponseDto.setDescription("A test description.");
        sampleResponseDto.setZipCode("12345-000");
        sampleResponseDto.setNeighborhood("Sample Neighborhood");
        sampleResponseDto.setAddress("Sample Address");
        sampleResponseDto.setNumber("123");
        sampleResponseDto.setComplement("Apt 1");
        sampleResponseDto.setHideExactAddress(false);
        sampleResponseDto.setSquareMeters(30.0);
        sampleResponseDto.setBuiltArea(28.0);
        sampleResponseDto.setBedrooms(1);
        sampleResponseDto.setBathrooms(1);
        sampleResponseDto.setParkingSpaces(0);
        sampleResponseDto.setAmenities("Wi-Fi, Furniture");
        sampleResponseDto.setFloor(1);
        sampleResponseDto.setCondominiumFee(150.0);
        sampleResponseDto.setPhotos("photo1.jpg");
        sampleResponseDto.setOwnerEmail("test@test.com");
    }

    @Test
    void testCreateProperty() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(sampleUser));
        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);

        PropertyResponseDTO result = propertyService.create(sampleRequestDto);

        assertNotNull(result);
        assertEquals(sampleProperty.getId(), result.getId());
        assertEquals(sampleProperty.getAdTitle(), result.getAdTitle());
        assertEquals(sampleUser.getEmail(), result.getOwnerEmail());
        assertEquals(1, result.getBedrooms());
        assertEquals(1, result.getBathrooms());
        assertEquals(0, result.getParkingSpaces());
        assertEquals(1, result.getFloor());

        verify(userRepository, times(1)).findById(userId);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testCreatePropertyOwnerNotFound() {
        UUID nonExistentOwnerId = UUID.randomUUID();
        sampleRequestDto.setOwnerId(nonExistentOwnerId);
        when(userRepository.findById(nonExistentOwnerId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.create(sampleRequestDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("Proprietário com ID " + nonExistentOwnerId + " não encontrado.", thrown.getReason());
        verify(userRepository, times(1)).findById(nonExistentOwnerId);
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testFindAllProperties() {
        when(propertyRepository.findAll()).thenReturn(Arrays.asList(sampleProperty));

        List<PropertyResponseDTO> results = propertyService.findAll();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(sampleProperty.getAdTitle(), results.get(0).getAdTitle());
        assertEquals(sampleUser.getEmail(), results.get(0).getOwnerEmail());

        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void testFindPropertyByIdFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(sampleProperty));

        PropertyResponseDTO result = propertyService.findById(propertyId);

        assertNotNull(result);
        assertEquals(sampleProperty.getId(), result.getId());
        assertEquals(sampleProperty.getAdTitle(), result.getAdTitle());
        assertEquals(sampleUser.getEmail(), result.getOwnerEmail());

        verify(propertyRepository, times(1)).findById(propertyId);
    }

    @Test
    void testFindPropertyByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(propertyRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.findById(nonExistentId);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Propriedade não encontrada", thrown.getReason());
        verify(propertyRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void testFindByOwner() {
        when(propertyRepository.findByOwnerId(userId)).thenReturn(Arrays.asList(sampleProperty));

        List<PropertyResponseDTO> results = propertyService.findByOwner(sampleUser);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(sampleProperty.getAdTitle(), results.get(0).getAdTitle());
        assertEquals(sampleUser.getEmail(), results.get(0).getOwnerEmail());

        verify(propertyRepository, times(1)).findByOwnerId(userId);
    }

    @Test
    void testFindByOwnerUnauthorized() {
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.findByOwner(null);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Usuário não autenticado", thrown.getReason());
        verify(propertyRepository, never()).findByOwnerId(any());
    }

    @Test
    void testUpdateProperty() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(sampleProperty));
        when(userRepository.findById(userId)).thenReturn(Optional.of(sampleUser));
        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);

        PropertyResponseDTO result = propertyService.update(propertyId, sampleRequestDto);

        assertNotNull(result);
        assertEquals(sampleProperty.getId(), result.getId());
        assertEquals(sampleRequestDto.getAdTitle(), result.getAdTitle());
        assertEquals(sampleUser.getEmail(), result.getOwnerEmail());

        verify(propertyRepository, times(1)).findById(propertyId);
        verify(userRepository, times(1)).findById(userId);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testUpdatePropertyNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(propertyRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.update(nonExistentId, sampleRequestDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Propriedade não encontrada", thrown.getReason());
        verify(propertyRepository, times(1)).findById(nonExistentId);
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testUpdatePropertyForbidden() {
        UUID differentUserId = UUID.randomUUID();
        sampleRequestDto.setOwnerId(differentUserId);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(sampleProperty));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.update(propertyId, sampleRequestDto);
        });

        assertEquals(HttpStatus.FORBIDDEN, thrown.getStatusCode());
        assertEquals("Somente o proprietário pode atualizar esta propriedade", thrown.getReason());
        verify(propertyRepository, times(1)).findById(propertyId);
        verify(userRepository, never()).findById(any());
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testUpdatePropertyOwnerNotFound() {
        UUID differentUserId = UUID.randomUUID();
        sampleRequestDto.setOwnerId(differentUserId);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(sampleProperty));
        when(userRepository.findById(differentUserId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.update(propertyId, sampleRequestDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("Proprietário com ID " + differentUserId + " não encontrado.", thrown.getReason());
        verify(propertyRepository, times(1)).findById(propertyId);
        verify(userRepository, times(1)).findById(differentUserId);
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testDeleteProperty() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(sampleProperty));
        doNothing().when(propertyRepository).deleteById(propertyId);

        propertyService.delete(propertyId, sampleUser);

        verify(propertyRepository, times(1)).findById(propertyId);
        verify(propertyRepository, times(1)).deleteById(propertyId);
    }

    @Test
    void testDeletePropertyNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(propertyRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.delete(nonExistentId, sampleUser);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Propriedade não encontrada", thrown.getReason());
        verify(propertyRepository, times(1)).findById(nonExistentId);
        verify(propertyRepository, never()).deleteById(any());
    }

    @Test
    void testDeletePropertyForbidden() {
        UUID differentUserId = UUID.randomUUID();
        User differentUser = new User();
        differentUser.setId(differentUserId);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(sampleProperty));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.delete(propertyId, differentUser);
        });

        assertEquals(HttpStatus.FORBIDDEN, thrown.getStatusCode());
        assertEquals("Somente o proprietário pode excluir esta propriedade", thrown.getReason());
        verify(propertyRepository, times(1)).findById(propertyId);
        verify(propertyRepository, never()).deleteById(any());
    }

    @Test
    void testDefaultValuesInToDto() {
        Property propertyWithNulls = new Property();
        propertyWithNulls.setId(propertyId);
        propertyWithNulls.setOwner(sampleUser);
        propertyWithNulls.setAdTitle("Test Kitnet");
        propertyWithNulls.setPropertyType("Kitnet");
        // Deixando bedrooms, bathrooms, parkingSpaces, floor como null

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(propertyWithNulls));

        PropertyResponseDTO result = propertyService.findById(propertyId);

        assertNotNull(result);
        assertEquals(0, result.getBedrooms());
        assertEquals(0, result.getBathrooms());
        assertEquals(0, result.getParkingSpaces());
        assertEquals(0, result.getFloor());
        verify(propertyRepository, times(1)).findById(propertyId);
    }
}