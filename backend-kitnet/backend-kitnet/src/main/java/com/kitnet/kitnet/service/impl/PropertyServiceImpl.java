package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.repository.PropertyRepository;
import com.kitnet.kitnet.repository.UserRepository;

import com.kitnet.kitnet.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;
// fix services, in controller data user is implemented, but in service i need validate if user egual a necessary user or user exist for create a property
    private PropertyResponseDto toDto(Property property) {
        PropertyResponseDto dto = new PropertyResponseDto();
        dto.setId(property.getId());
        dto.setAdTitle(property.getAdTitle());
        dto.setCity(property.getCity());
        dto.setState(property.getState());
        dto.setPurpose(property.getPurpose());
        dto.setRentValue(property.getRentValue());
        dto.setPropertyType(property.getPropertyType());
        dto.setDescription(property.getDescription());
        dto.setOwnerConfirmation(property.getOwnerConfirmation());
        if (property.getOwner() != null) {
            dto.setOwnerId(property.getOwner().getEmail());
        }
        return dto;
    }

    private Property fromDto(PropertyRequestDto dto) {
        Property p = new Property();
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Proprietário com ID " + dto.getOwnerId() + " não encontrado."));
        p.setOwner(owner);

        p.setAdTitle(dto.getAdTitle());
        p.setCity(dto.getCity());
        p.setState(dto.getState());
        p.setPurpose(dto.getPurpose());
        p.setRentValue(dto.getRentValue());
        p.setPropertyType(dto.getPropertyType());
        p.setDescription(dto.getDescription());
        p.setZipCode(dto.getZipCode());
        p.setNeighborhood(dto.getNeighborhood());
        p.setAddress(dto.getAddress());
        p.setNumber(dto.getNumber());
        p.setComplement(dto.getComplement());
        p.setHideExactAddress(dto.getHideExactAddress());
        p.setSquareMeters(dto.getSquareMeters());
        p.setBuiltArea(dto.getBuiltArea());
        p.setBedrooms(dto.getBedrooms());
        p.setBathrooms(dto.getBathrooms());
        p.setParkingSpaces(dto.getParkingSpaces());
        p.setAmenities(dto.getAmenities());
        p.setFloor(dto.getFloor());
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
    public PropertyResponseDto findById(Long id) {
        return propertyRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    @Override
    public PropertyResponseDto update(Long id, PropertyRequestDto dto) {
        Property existing = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Proprietário com ID " + dto.getOwnerId() + " não encontrado."));
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
        existing.setBedrooms(dto.getBedrooms());
        existing.setBathrooms(dto.getBathrooms());
        existing.setParkingSpaces(dto.getParkingSpaces());
        existing.setAmenities(dto.getAmenities());
        existing.setFloor(dto.getFloor());
        existing.setCondominiumFee(dto.getCondominiumFee());
        existing.setPhotos(dto.getPhotos());
        existing.setOwnerConfirmation(dto.getOwnerConfirmation());
        existing.setTermsAgreement(dto.getTermsAgreement());

        return toDto(propertyRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        propertyRepository.deleteById(id);
    }
}
