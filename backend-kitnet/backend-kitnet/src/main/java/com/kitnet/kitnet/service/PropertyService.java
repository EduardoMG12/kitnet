package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.property.PropertyResponseDTO;
import com.kitnet.kitnet.dto.property.PropertyRequestDTO;
import com.kitnet.kitnet.model.User;

import java.util.List;
import java.util.UUID;

public interface PropertyService {
    PropertyResponseDTO create(PropertyRequestDTO dto, User authenticatedUser);
    List<PropertyResponseDTO> findAll();
    PropertyResponseDTO findById(UUID id);
    PropertyResponseDTO update(UUID id, PropertyRequestDTO dto);
    void delete(UUID id, User currentUser);
    List<PropertyResponseDTO> findByOwner(User currentUser);
}