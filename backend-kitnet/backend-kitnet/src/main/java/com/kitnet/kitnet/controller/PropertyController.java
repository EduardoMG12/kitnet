package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public PropertyResponseDto create(@RequestBody PropertyRequestDto dto) {
        return propertyService.create(dto);
    }

    @GetMapping
    public List<PropertyResponseDto> getAll() {
        return propertyService.findAll();
    }

    @GetMapping("/{id}")
    public PropertyResponseDto getById(@PathVariable Long id) {
        return propertyService.findById(id);
    }

    @PutMapping("/{id}")
    public PropertyResponseDto update(@PathVariable Long id, @RequestBody PropertyRequestDto dto) {
        return propertyService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        propertyService.delete(id);
    }
}
