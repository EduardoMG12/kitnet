package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyResponseDto> create(
            @Valid @RequestBody PropertyRequestDto dto,
            @AuthenticationPrincipal User currentUser) {
        System.out.println("POST /properties, User: " + currentUser);
        if (currentUser == null || currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        dto.setOwnerId(currentUser.getId());
        PropertyResponseDto createdProperty = propertyService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
    }

    @GetMapping
    public List<PropertyResponseDto> getAll() {
        return propertyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getById(@PathVariable UUID id) {
        PropertyResponseDto property = propertyService.findById(id);
        return ResponseEntity.ok(property);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PropertyRequestDto dto, @AuthenticationPrincipal User currentUser) {
        System.out.println("PUT /properties/" + id + ", DTO: " + dto + ", User: " + currentUser);
        if (currentUser == null || currentUser.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
        }
        dto.setOwnerId(currentUser.getId());
        PropertyResponseDto updatedProperty = propertyService.update(id, dto);
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id, @AuthenticationPrincipal User currentUser) {
        System.out.println("DELETE /properties/" + id + ", User: " + currentUser);
        if (currentUser == null || currentUser.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
        }
        propertyService.delete(id, currentUser);
    }
}