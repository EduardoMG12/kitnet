package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.dto.PropertyRequestDto;

import java.util.List;

public interface PropertyService {
    PropertyResponseDto create(PropertyRequestDto dto);
    List<PropertyResponseDto> findAll();
    PropertyResponseDto findById(Long id);
    PropertyResponseDto update(Long id, PropertyRequestDto dto);
    void delete(Long id);
}
