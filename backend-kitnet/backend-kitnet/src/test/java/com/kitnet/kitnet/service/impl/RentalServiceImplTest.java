package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.rental.RentalRequestDTO;
import com.kitnet.kitnet.dto.rental.RentalResponseDTO;

import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.Rental;
import com.kitnet.kitnet.model.RentalStatus;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.RoleName;
import com.kitnet.kitnet.repository.PropertyRepository;
import com.kitnet.kitnet.repository.RentalRepository;
import com.kitnet.kitnet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private User tenantUser;
    private User ownerUser;
    private User realEstateAgentUser;
    private User unauthorizedUser;
    private Property sampleProperty;
    private Rental sampleRental;
    private RentalRequestDTO sampleRequestDto;
    private UUID rentalId;

    @BeforeEach
    void setUp() {
        tenantUser = new User();
        tenantUser.setId(UUID.randomUUID());
        tenantUser.setEmail("tenant@example.com");
        tenantUser.setRoleName(RoleName.LESSEE);
        tenantUser.setFirstName("Tenant");
        tenantUser.setLastName("User");
        tenantUser.setPassword("password");
        tenantUser.setAcceptTerms(true);
        tenantUser.setCpf("11111111111");

        ownerUser = new User();
        ownerUser.setId(UUID.randomUUID());
        ownerUser.setEmail("owner@example.com");
        ownerUser.setRoleName(RoleName.LESSOR);
        ownerUser.setFirstName("Owner");
        ownerUser.setLastName("User");
        ownerUser.setPassword("password");
        ownerUser.setAcceptTerms(true);
        ownerUser.setCpf("22222222222");

        realEstateAgentUser = new User();
        realEstateAgentUser.setId(UUID.randomUUID());
        realEstateAgentUser.setEmail("agent@example.com");
        realEstateAgentUser.setRoleName(RoleName.REAL_ESTATE_AGENT);
        realEstateAgentUser.setFirstName("Agent");
        realEstateAgentUser.setLastName("User");
        realEstateAgentUser.setPassword("password");
        realEstateAgentUser.setAcceptTerms(true);
        realEstateAgentUser.setCpf("33333333333");

        unauthorizedUser = new User();
        unauthorizedUser.setId(UUID.randomUUID());
        unauthorizedUser.setEmail("unauthorized@example.com");
        unauthorizedUser.setRoleName(RoleName.LESSEE);
        unauthorizedUser.setFirstName("Unauthorized");
        unauthorizedUser.setLastName("User");
        unauthorizedUser.setPassword("password");
        unauthorizedUser.setAcceptTerms(true);
        unauthorizedUser.setCpf("44444444444");

        sampleProperty = new Property();
        sampleProperty.setId(UUID.randomUUID());
        sampleProperty.setOwner(ownerUser);
        sampleProperty.setAdTitle("Apartamento Teste");
        sampleProperty.setPropertyType("Apartment");
        sampleProperty.setPurpose("Rent");
        sampleProperty.setRentValue(1500.0);
        sampleProperty.setOwnerConfirmation(true);
        sampleProperty.setTermsAgreement(true);
        sampleProperty.setCity("City");
        sampleProperty.setState("ST");
        sampleProperty.setBedrooms(1);
        sampleProperty.setBathrooms(1);
        sampleProperty.setParkingSpaces(0);
        sampleProperty.setFloor(1);


        rentalId = UUID.randomUUID();
        sampleRental = new Rental();
        sampleRental.setId(rentalId);
        sampleRental.setTenant(tenantUser);
        sampleRental.setProperty(sampleProperty);
        sampleRental.setStartDate(LocalDate.now());
        sampleRental.setEndDate(LocalDate.now().plusMonths(12));
        sampleRental.setMonthlyRent(1500.0);
        sampleRental.setActive(true);
        sampleRental.setStatus(RentalStatus.ACTIVE);

        sampleRequestDto = new RentalRequestDTO();
        sampleRequestDto.setTenantId(tenantUser.getId());
        sampleRequestDto.setPropertyId(sampleProperty.getId());
        sampleRequestDto.setStartDate(LocalDate.now());
        sampleRequestDto.setEndDate(LocalDate.now().plusMonths(12));
        sampleRequestDto.setMonthlyRent(1500.0);
    }

    @Test
    void testCreateRentalSuccessByOwner() {
        when(userRepository.findById(tenantUser.getId())).thenReturn(Optional.of(tenantUser));
        when(propertyRepository.findById(sampleProperty.getId())).thenReturn(Optional.of(sampleProperty));
        when(rentalRepository.save(any(Rental.class))).thenReturn(sampleRental);

        RentalResponseDTO result = rentalService.createRental(sampleRequestDto, ownerUser);

        assertNotNull(result);
        assertEquals(rentalId, result.getId());
        assertEquals(tenantUser.getEmail(), result.getTenantEmail());
        assertEquals(sampleProperty.getAdTitle(), result.getPropertyTitle());
        assertEquals(RentalStatus.ACTIVE, result.getStatus());
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testCreateRentalSuccessByAgent() {
        when(userRepository.findById(tenantUser.getId())).thenReturn(Optional.of(tenantUser));
        when(propertyRepository.findById(sampleProperty.getId())).thenReturn(Optional.of(sampleProperty));
        when(rentalRepository.save(any(Rental.class))).thenReturn(sampleRental);

        RentalResponseDTO result = rentalService.createRental(sampleRequestDto, realEstateAgentUser);

        assertNotNull(result);
        assertEquals(rentalId, result.getId());
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testCreateRentalForbiddenByTenant() {
        assertThrows(ResponseStatusException.class, () -> {
            rentalService.createRental(sampleRequestDto, tenantUser);
        }, "Somente o proprietário ou um corretor pode criar um aluguel para esta propriedade.");
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testCreateRentalInvalidDates() {
        RentalRequestDTO invalidDto = new RentalRequestDTO();
        BeanUtils.copyProperties(sampleRequestDto, invalidDto);
        invalidDto.setStartDate(LocalDate.now().plusMonths(12));
        invalidDto.setEndDate(LocalDate.now());

        when(userRepository.findById(tenantUser.getId())).thenReturn(Optional.of(tenantUser));
        when(propertyRepository.findById(sampleProperty.getId())).thenReturn(Optional.of(sampleProperty));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.createRental(invalidDto, ownerUser);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("A data de início não pode ser depois da data de término.", exception.getReason());
        verify(rentalRepository, never()).save(any(Rental.class));
    }


    @Test
    void testFindRentalByIdSuccessByOwner() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        RentalResponseDTO result = rentalService.findRentalById(rentalId, ownerUser);

        assertNotNull(result);
        assertEquals(rentalId, result.getId());
        verify(rentalRepository, times(1)).findById(rentalId);
    }

    @Test
    void testFindRentalByIdSuccessByTenant() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        RentalResponseDTO result = rentalService.findRentalById(rentalId, tenantUser);

        assertNotNull(result);
        assertEquals(rentalId, result.getId());
        verify(rentalRepository, times(1)).findById(rentalId);
    }

    @Test
    void testFindRentalByIdNotFound() {
        when(rentalRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.findRentalById(UUID.randomUUID(), ownerUser);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Aluguel não encontrado.", exception.getReason());
        verify(rentalRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testFindRentalByIdForbidden() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.findRentalById(rentalId, unauthorizedUser);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Você não tem permissão para visualizar este aluguel.", exception.getReason());
        verify(rentalRepository, times(1)).findById(rentalId);
    }

    @Test
    void testProposePriceChangeSuccessByOwner() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(sampleRental);

        RentalResponseDTO result = rentalService.proposePriceChange(rentalId, 1800.0, ownerUser);

        assertNotNull(result);
        assertEquals(1800.0, result.getProposedMonthlyRent());
        assertEquals(RentalStatus.PENDING_APPROVAL, result.getStatus());
        assertNotNull(result.getLastPriceUpdateDate());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testProposePriceChangeForbiddenByTenant() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.proposePriceChange(rentalId, 1800.0, tenantUser);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Somente o proprietário ou corretor pode propor alteração de preço.", exception.getReason());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testProposePriceChangeAlreadyPending() {
        sampleRental.setStatus(RentalStatus.PENDING_APPROVAL);
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.proposePriceChange(rentalId, 1800.0, ownerUser);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Já existe uma proposta de alteração de preço pendente para este aluguel.", exception.getReason());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testApprovePriceChangeSuccessByTenant() {
        sampleRental.setStatus(RentalStatus.PENDING_APPROVAL);
        sampleRental.setProposedMonthlyRent(1800.0);
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(sampleRental);

        RentalResponseDTO result = rentalService.approvePriceChange(rentalId, tenantUser);

        assertNotNull(result);
        assertEquals(1800.0, result.getMonthlyRent());
        assertNull(result.getProposedMonthlyRent());
        assertEquals(RentalStatus.ACTIVE, result.getStatus());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testApprovePriceChangeForbiddenByOwner() {
        sampleRental.setStatus(RentalStatus.PENDING_APPROVAL);
        sampleRental.setProposedMonthlyRent(1800.0);
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.approvePriceChange(rentalId, ownerUser);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Somente o locatário pode aprovar a alteração de preço.", exception.getReason());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testApprovePriceChangeNoPendingProposal() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.approvePriceChange(rentalId, tenantUser);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Não há proposta de alteração de preço pendente para aprovação.", exception.getReason());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testRejectPriceChangeSuccessByTenant() {
        sampleRental.setStatus(RentalStatus.PENDING_APPROVAL);
        sampleRental.setProposedMonthlyRent(1800.0);
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(sampleRental);

        RentalResponseDTO result = rentalService.rejectPriceChange(rentalId, tenantUser);

        assertNotNull(result);
        assertEquals(1500.0, result.getMonthlyRent());
        assertNull(result.getProposedMonthlyRent());
        assertEquals(RentalStatus.ACTIVE, result.getStatus());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testRejectPriceChangeForbiddenByOwner() {
        sampleRental.setStatus(RentalStatus.PENDING_APPROVAL);
        sampleRental.setProposedMonthlyRent(1800.0);
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.rejectPriceChange(rentalId, ownerUser);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Somente o locatário pode rejeitar a alteração de preço.", exception.getReason());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testTerminateContractSuccessByOwner() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(sampleRental);

        RentalResponseDTO result = rentalService.terminateContract(rentalId, ownerUser);

        assertNotNull(result);
        assertFalse(result.getActive());
        assertEquals(RentalStatus.TERMINATED, result.getStatus());
        assertEquals(LocalDate.now(), result.getEndDate());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testTerminateContractSuccessByTenant() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(sampleRental);

        RentalResponseDTO result = rentalService.terminateContract(rentalId, tenantUser);

        assertNotNull(result);
        assertFalse(result.getActive());
        assertEquals(RentalStatus.TERMINATED, result.getStatus());
        assertEquals(LocalDate.now(), result.getEndDate());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testTerminateContractForbidden() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            rentalService.terminateContract(rentalId, unauthorizedUser);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Somente o locador/corretor ou locatário pode solicitar o término do contrato.", exception.getReason());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testBreakContractSuccessByOwner() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(sampleRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(sampleRental);

        RentalResponseDTO result = rentalService.breakContract(rentalId, ownerUser);

        assertNotNull(result);
        assertFalse(result.getActive());
        assertEquals(RentalStatus.BROKEN, result.getStatus());
        assertEquals(LocalDate.now(), result.getEndDate());
        verify(rentalRepository, times(1)).findById(rentalId);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }
}