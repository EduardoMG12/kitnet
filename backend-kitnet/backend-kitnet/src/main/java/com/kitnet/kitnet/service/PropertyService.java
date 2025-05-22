package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.model.User;

import java.util.List;
import java.util.UUID;

public interface PropertyService {
    PropertyResponseDto create(PropertyRequestDto dto);
    List<PropertyResponseDto> findAll();
    PropertyResponseDto findById(UUID id);
    PropertyResponseDto update(UUID id, PropertyRequestDto dto);
    void delete(UUID id, User currentUser); // Atualizado para incluir currentUser
}