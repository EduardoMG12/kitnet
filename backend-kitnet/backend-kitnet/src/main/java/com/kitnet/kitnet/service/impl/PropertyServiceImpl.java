package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.repository.PropertyRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    private PropertyResponseDto toDto(Property property) {
        PropertyResponseDto dto = new PropertyResponseDto();
        dto.setId(property.getId());
        dto.setPropertyType(property.getPropertyType());
        dto.setAdTitle(property.getAdTitle());
        dto.setDescription(property.getDescription());
        dto.setPurpose(property.getPurpose());
        dto.setRentValue(property.getRentValue());
        dto.setZipCode(property.getZipCode());
        dto.setState(property.getState());
        dto.setCity(property.getCity());
        dto.setNeighborhood(property.getNeighborhood());
        dto.setAddress(property.getAddress());
        dto.setNumber(property.getNumber());
        dto.setComplement(property.getComplement());
        dto.setHideExactAddress(property.getHideExactAddress());
        dto.setSquareMeters(property.getSquareMeters());
        dto.setBuiltArea(property.getBuiltArea());
        dto.setBedrooms(property.getBedrooms() != null ? property.getBedrooms() : 0);
        dto.setBathrooms(property.getBathrooms() != null ? property.getBathrooms() : 0);
        dto.setParkingSpaces(property.getParkingSpaces() != null ? property.getParkingSpaces() : 0);
        dto.setAmenities(property.getAmenities());
        dto.setFloor(property.getFloor() != null ? property.getFloor() : 0);
        dto.setCondominiumFee(property.getCondominiumFee());
        dto.setPhotos(property.getPhotos());
        dto.setOwnerConfirmation(property.getOwnerConfirmation());
        dto.setTermsAgreement(property.getTermsAgreement());
        if (property.getOwner() != null) {
            dto.setOwnerEmail(property.getOwner().getEmail());
        }
        return dto;
    }

    private Property fromDto(PropertyRequestDto dto) {
        Property p = new Property();
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Proprietário com ID " + dto.getOwnerId() + " não encontrado."));
        p.setOwner(owner);
        p.setPropertyType(dto.getPropertyType());
        p.setAdTitle(dto.getAdTitle());
        p.setDescription(dto.getDescription());
        p.setPurpose(dto.getPurpose());
        p.setRentValue(dto.getRentValue());
        p.setZipCode(dto.getZipCode());
        p.setState(dto.getState());
        p.setCity(dto.getCity());
        p.setNeighborhood(dto.getNeighborhood());
        p.setAddress(dto.getAddress());
        p.setNumber(dto.getNumber());
        p.setComplement(dto.getComplement());
        p.setHideExactAddress(dto.getHideExactAddress());
        p.setSquareMeters(dto.getSquareMeters());
        p.setBuiltArea(dto.getBuiltArea());
        p.setBedrooms(dto.getBedrooms() != null ? dto.getBedrooms() : 0);
        p.setBathrooms(dto.getBathrooms() != null ? dto.getBathrooms() : 0);
        p.setParkingSpaces(dto.getParkingSpaces() != null ? dto.getParkingSpaces() : 0);
        p.setAmenities(dto.getAmenities());
        p.setFloor(dto.getFloor() != null ? dto.getFloor() : 0);
        p.setCondominiumFee(dto.getCondominiumFee());
        p.setPhotos(dto.getPhotos());
        p.setOwnerConfirmation(dto.getOwnerConfirmation());
        p.setTermsAgreement(dto.getTermsAgreement());
        return p;
    }

    @Override
    public PropertyResponseDto create(PropertyRequestDto dto) {
        Property property = propertyRepository.save(fromDto(dto));
        return toDto(property);
    }

    @Override
    public List<PropertyResponseDto> findAll() {
        return propertyRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyResponseDto findById(UUID id) {
        return propertyRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    @Override
    public PropertyResponseDto update(UUID id, PropertyRequestDto dto) {
        Property existing = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Propriedade não encontrada"));

        // Verificar se o usuário autenticado é o proprietário da propriedade
        if (!existing.getOwner().getId().equals(dto.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o proprietário pode atualizar esta propriedade");
        }

        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proprietário com ID " + dto.getOwnerId() + " não encontrado."));
        existing.setOwner(owner);

        existing.setPropertyType(dto.getPropertyType());
        existing.setAdTitle(dto.getAdTitle());
        existing.setDescription(dto.getDescription());
        existing.setPurpose(dto.getPurpose());
        existing.setRentValue(dto.getRentValue());
        existing.setZipCode(dto.getZipCode());
        existing.setState(dto.getState());
        existing.setCity(dto.getCity());
        existing.setNeighborhood(dto.getNeighborhood());
        existing.setAddress(dto.getAddress());
        existing.setNumber(dto.getNumber());
        existing.setComplement(dto.getComplement());
        existing.setHideExactAddress(dto.getHideExactAddress());
        existing.setSquareMeters(dto.getSquareMeters());
        existing.setBuiltArea(dto.getBuiltArea());
        existing.setBedrooms(dto.getBedrooms() != null ? dto.getBedrooms() : 0);
        existing.setBathrooms(dto.getBathrooms() != null ? dto.getBathrooms() : 0);
        existing.setParkingSpaces(dto.getParkingSpaces() != null ? dto.getParkingSpaces() : 0);
        existing.setAmenities(dto.getAmenities());
        existing.setFloor(dto.getFloor() != null ? dto.getFloor() : 0);
        existing.setCondominiumFee(dto.getCondominiumFee());
        existing.setPhotos(dto.getPhotos());
        existing.setOwnerConfirmation(dto.getOwnerConfirmation());
        existing.setTermsAgreement(dto.getTermsAgreement());

        return toDto(propertyRepository.save(existing));
    }

    @Override
    public void delete(UUID id, User currentUser) {
        Property existing = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Propriedade não encontrada"));
        if (!existing.getOwner().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o proprietário pode excluir esta propriedade");
        }
        propertyRepository.deleteById(id);
    }
}