package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.UserType;
import com.kitnet.kitnet.repository.PropertyRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Extensão do JUnit para habilitar o Mockito
@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    private PropertyRequestDto sampleRequestDto;
    private Property sampleProperty;
    private PropertyResponseDto sampleResponseDto;
    private User sampleOwner;
    private UUID sampleOwnerId;
    private UUID samplePropertyId;

    @BeforeEach
    void setUp() {
        sampleOwnerId = UUID.randomUUID();
        samplePropertyId = UUID.randomUUID();


        sampleOwner = new User();
        sampleOwner.setId(sampleOwnerId);
        sampleOwner.setEmail("owner@example.com");
        sampleOwner.setFirstName("Owner");
        sampleOwner.setLastName("User");
        sampleOwner.setPassword("hashedpass");
        sampleOwner.setAcceptTerms(true);
        sampleOwner.setCpf("11122233344");
        sampleOwner.setUserType(UserType.LESSOR);



        sampleRequestDto = new PropertyRequestDto();
        sampleRequestDto.setOwnerId(sampleOwnerId);
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
        sampleProperty.setId(samplePropertyId);
        sampleProperty.setOwner(sampleOwner);
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
        sampleResponseDto.setId(samplePropertyId);
        sampleResponseDto.setAdTitle("Test Kitnet");
        sampleResponseDto.setCity("Sample City");
        sampleResponseDto.setState("SS");
        sampleResponseDto.setPurpose("Rent");
        sampleResponseDto.setRentValue(1000.0);
        sampleResponseDto.setPropertyType("Kitnet");
        sampleResponseDto.setOwnerConfirmation(true);
        sampleResponseDto.setDescription("A test description.");
        sampleResponseDto.setOwnerEmail(sampleOwner.getEmail());
    }

    @Test
    void testCreateProperty() {

        when(userRepository.findById(sampleOwnerId)).thenReturn(Optional.of(sampleOwner));

        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);

        PropertyResponseDto result = propertyService.create(sampleRequestDto);


        assertNotNull(result);
        assertEquals(sampleProperty.getId(), result.getId());
        assertEquals(sampleProperty.getAdTitle(), result.getAdTitle());
        assertEquals(sampleOwner.getEmail(), result.getOwnerEmail());


        verify(userRepository, times(1)).findById(sampleOwnerId);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testCreatePropertyOwnerNotFound() {

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            propertyService.create(sampleRequestDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Proprietário com ID"));
        assertTrue(exception.getReason().contains("não encontrado."));

        verify(userRepository, times(1)).findById(sampleOwnerId);
        verify(propertyRepository, never()).save(any(Property.class));
    }


    @Test
    void testFindAllProperties() {

        when(propertyRepository.findAll()).thenReturn(Arrays.asList(sampleProperty));

        List<PropertyResponseDto> results = propertyService.findAll();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(sampleProperty.getAdTitle(), results.get(0).getAdTitle());
        assertEquals(sampleOwner.getEmail(), results.get(0).getOwnerEmail());

        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void testFindPropertyByIdFound() {

        when(propertyRepository.findById(samplePropertyId)).thenReturn(Optional.of(sampleProperty));

        PropertyResponseDto result = propertyService.findById(samplePropertyId);

        assertNotNull(result);
        assertEquals(sampleProperty.getId(), result.getId());
        assertEquals(sampleProperty.getAdTitle(), result.getAdTitle());
        assertEquals(sampleOwner.getEmail(), result.getOwnerEmail());

        verify(propertyRepository, times(1)).findById(samplePropertyId);
    }

    @Test
    void testFindPropertyByIdNotFound() {

        when(propertyRepository.findById(any(UUID.class))).thenReturn(Optional.empty());


        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.findById(UUID.randomUUID());
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Propriedade não encontrada", thrown.getReason());
        verify(propertyRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testUpdatePropertySuccess() {

        when(propertyRepository.findById(samplePropertyId)).thenReturn(Optional.of(sampleProperty));

        when(userRepository.findById(sampleOwnerId)).thenReturn(Optional.of(sampleOwner));

        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);


        sampleRequestDto.setAdTitle("Updated Kitnet Title");
        sampleRequestDto.setRentValue(1200.0);

        PropertyResponseDto result = propertyService.update(samplePropertyId, sampleRequestDto);

        assertNotNull(result);
        assertEquals(samplePropertyId, result.getId());
        assertEquals("Updated Kitnet Title", result.getAdTitle());
        assertEquals(1200.0, result.getRentValue());

        verify(propertyRepository, times(1)).findById(samplePropertyId);
        verify(userRepository, times(1)).findById(sampleOwnerId);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testUpdatePropertyNotFound() {

        when(propertyRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.update(UUID.randomUUID(), sampleRequestDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Propriedade não encontrada", thrown.getReason());
        verify(propertyRepository, times(1)).findById(any(UUID.class));
        verify(userRepository, never()).findById(any(UUID.class));
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testUpdatePropertyForbidden() {

        User differentUser = new User();
        differentUser.setId(UUID.randomUUID());
        differentUser.setEmail("different@example.com");
        differentUser.setFirstName("Diff");
        differentUser.setLastName("User");
        differentUser.setPassword("pass");
        differentUser.setAcceptTerms(true);
        differentUser.setCpf("99988877766");
        differentUser.setUserType(UserType.LESSEE);


        when(propertyRepository.findById(samplePropertyId)).thenReturn(Optional.of(sampleProperty));





        sampleRequestDto.setOwnerId(differentUser.getId());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.update(samplePropertyId, sampleRequestDto);
        });

        assertEquals(HttpStatus.FORBIDDEN, thrown.getStatusCode());
        assertEquals("Somente o proprietário pode atualizar esta propriedade", thrown.getReason());

        verify(propertyRepository, times(1)).findById(samplePropertyId);

        verify(userRepository, never()).findById(any(UUID.class));
        verify(propertyRepository, never()).save(any(Property.class));
    }


    @Test
    void testDeletePropertySuccess() {

        when(propertyRepository.findById(samplePropertyId)).thenReturn(Optional.of(sampleProperty));

        doNothing().when(propertyRepository).deleteById(samplePropertyId);


        User authorizedUser = new User();
        authorizedUser.setId(sampleOwnerId);
        authorizedUser.setEmail("authorized@example.com");
        authorizedUser.setFirstName("Auth");
        authorizedUser.setLastName("User");
        authorizedUser.setPassword("pass");
        authorizedUser.setAcceptTerms(true);
        authorizedUser.setCpf("12345678900");
        authorizedUser.setUserType(UserType.LESSOR);


        propertyService.delete(samplePropertyId, authorizedUser);


        verify(propertyRepository, times(1)).findById(samplePropertyId);
        verify(propertyRepository, times(1)).deleteById(samplePropertyId);
    }

    @Test
    void testDeletePropertyNotFound() {

        when(propertyRepository.findById(any(UUID.class))).thenReturn(Optional.empty());


        User anyUser = new User();
        anyUser.setId(UUID.randomUUID());
        anyUser.setEmail("any@example.com");
        anyUser.setFirstName("Any");
        anyUser.setLastName("User");
        anyUser.setPassword("pass");
        anyUser.setAcceptTerms(true);
        anyUser.setCpf("11122233344");
        anyUser.setUserType(UserType.LESSEE);


        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.delete(UUID.randomUUID(), anyUser);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Propriedade não encontrada", thrown.getReason());
        verify(propertyRepository, times(1)).findById(any(UUID.class));
        verify(propertyRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void testDeletePropertyForbidden() {

        when(propertyRepository.findById(samplePropertyId)).thenReturn(Optional.of(sampleProperty));


        User differentUser = new User();
        differentUser.setId(UUID.randomUUID());
        differentUser.setEmail("different@example.com");
        differentUser.setFirstName("Diff");
        differentUser.setLastName("User");
        differentUser.setPassword("pass");
        differentUser.setAcceptTerms(true);
        differentUser.setCpf("99988877766");
        differentUser.setUserType(UserType.LESSEE);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.delete(samplePropertyId, differentUser);
        });

        assertEquals(HttpStatus.FORBIDDEN, thrown.getStatusCode());
        assertEquals("Somente o proprietário pode excluir esta propriedade", thrown.getReason());
        verify(propertyRepository, times(1)).findById(samplePropertyId);
        verify(propertyRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void testFindByOwnerSuccess() {

        User ownerUser = new User();
        UUID ownerUserId = UUID.randomUUID();
        ownerUser.setId(ownerUserId);
        ownerUser.setEmail("owner@example.com");
        ownerUser.setFirstName("Owner");
        ownerUser.setLastName("User");
        ownerUser.setPassword("hashedpass");
        ownerUser.setAcceptTerms(true);
        ownerUser.setCpf("11122233344");
        ownerUser.setUserType(UserType.LESSOR);


        Property prop1 = new Property();
        prop1.setId(UUID.randomUUID());
        prop1.setAdTitle("Prop1");
        prop1.setOwner(ownerUser);
        prop1.setOwnerConfirmation(true);
        prop1.setTermsAgreement(true);
        prop1.setPropertyType("Apartment");
        prop1.setPurpose("Rent");
        prop1.setRentValue(1000.0);
        prop1.setCity("City1");
        prop1.setState("S1");
        prop1.setBedrooms(1); prop1.setBathrooms(1); prop1.setParkingSpaces(0); prop1.setFloor(1);


        Property prop2 = new Property();
        prop2.setId(UUID.randomUUID());
        prop2.setAdTitle("Prop2");
        prop2.setOwner(ownerUser);
        prop2.setOwnerConfirmation(true);
        prop2.setTermsAgreement(true);
        prop2.setPropertyType("House");
        prop2.setPurpose("Sale");
        prop2.setRentValue(200000.0);
        prop2.setCity("City2");
        prop2.setState("S2");
        prop2.setBedrooms(1); prop2.setBathrooms(1); prop2.setParkingSpaces(0); prop2.setFloor(1);


        when(propertyRepository.findByOwnerId(ownerUserId)).thenReturn(Arrays.asList(prop1, prop2));

        List<PropertyResponseDto> results = propertyService.findByOwner(ownerUser);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Prop1", results.get(0).getAdTitle());
        assertEquals("Prop2", results.get(1).getAdTitle());
        assertEquals(ownerUser.getEmail(), results.get(0).getOwnerEmail());
        assertEquals(ownerUser.getEmail(), results.get(1).getOwnerEmail());

        verify(propertyRepository, times(1)).findByOwnerId(ownerUserId);
    }

    @Test
    void testFindByOwnerUnauthenticatedUser() {

        User unauthenticatedUser = null;

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.findByOwner(unauthenticatedUser);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Usuário não autenticado", thrown.getReason());
        verify(propertyRepository, never()).findByOwnerId(any(UUID.class));
    }

    @Test
    void testFindByOwnerUserWithNullId() {

        User userWithNullId = new User();
        userWithNullId.setEmail("test@example.com");
        userWithNullId.setFirstName("Test");
        userWithNullId.setLastName("User");
        userWithNullId.setPassword("pass");
        userWithNullId.setAcceptTerms(true);
        userWithNullId.setCpf("11122233344");
        userWithNullId.setUserType(UserType.LESSEE);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            propertyService.findByOwner(userWithNullId);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Usuário não autenticado", thrown.getReason());
        verify(propertyRepository, never()).findByOwnerId(any(UUID.class));
    }

    @Test
    void testFindByOwnerNoPropertiesFound() {
        User ownerUser = new User();
        UUID ownerUserId = UUID.randomUUID();
        ownerUser.setId(ownerUserId);
        ownerUser.setEmail("owner@example.com");
        ownerUser.setFirstName("Owner");
        ownerUser.setLastName("User");
        ownerUser.setPassword("hashedpass");
        ownerUser.setAcceptTerms(true);
        ownerUser.setCpf("11122233344");
        ownerUser.setUserType(UserType.LESSOR);


        when(propertyRepository.findByOwnerId(ownerUserId)).thenReturn(Collections.emptyList());

        List<PropertyResponseDto> results = propertyService.findByOwner(ownerUser);

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(propertyRepository, times(1)).findByOwnerId(ownerUserId);
    }
}
