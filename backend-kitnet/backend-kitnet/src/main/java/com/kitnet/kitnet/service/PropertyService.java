package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.PropertyResponseDTO;
import com.kitnet.kitnet.dto.PropertyRequestDTO;
import com.kitnet.kitnet.model.User;

import java.util.List;
import java.util.UUID;

public interface PropertyService {
    PropertyResponseDTO create(PropertyRequestDTO dto);
    List<PropertyResponseDTO> findAll();
    PropertyResponseDTO findById(UUID id);
    PropertyResponseDTO update(UUID id, PropertyRequestDTO dto);
    void delete(UUID id, User currentUser);
    List<PropertyResponseDTO> findByOwner(User currentUser); // Novo método
}