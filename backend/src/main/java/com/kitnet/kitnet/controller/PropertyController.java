package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.property.PropertyRequestDTO;
import com.kitnet.kitnet.dto.property.PropertyResponseDTO;
import com.kitnet.kitnet.dto.property.PropertyImageDTO;
import com.kitnet.kitnet.exception.*;
import com.kitnet.kitnet.mapper.PropertyMapper;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.PropertyImage;
import com.kitnet.kitnet.model.User;

import com.kitnet.kitnet.model.enums.PropertyAccountVerificationStatus;
import com.kitnet.kitnet.service.PropertyService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<PropertyResponseDTO>> getAllAvailableProperties() {
        List<Property> properties = propertyService.getAllAvailableProperties();
        return ResponseEntity.ok(PropertyMapper.toPropertyResponseDTOList(properties));
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyResponseDTO> getPropertyById(@PathVariable UUID propertyId) throws ResourceNotFoundException {
        Property property = propertyService.getPropertyById(propertyId);
        return ResponseEntity.ok(PropertyMapper.toPropertyResponseDTO(property));
    }

    @GetMapping("/{propertyId}/images")
    public ResponseEntity<List<PropertyImageDTO>> getPropertyImages(@PathVariable UUID propertyId) throws ResourceNotFoundException {
        List<PropertyImage> images = propertyService.getPropertyImages(propertyId);
        return ResponseEntity.ok(PropertyMapper.mapPropertyImagesToDTOs((Set<PropertyImage>) images));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PropertyResponseDTO> createProperty(
            @Valid @RequestBody PropertyRequestDTO dto,
            @AuthenticationPrincipal User authenticatedUser
    ) throws UserNotFoundException, InvalidOperationException {
        Property property = PropertyMapper.toProperty(dto);
        Property createdProperty = propertyService.createProperty(property, authenticatedUser.getId());
        return new ResponseEntity<>(PropertyMapper.toPropertyResponseDTO(createdProperty), HttpStatus.CREATED);
    }

    @PutMapping("/{propertyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PropertyResponseDTO> updateProperty(
            @PathVariable UUID propertyId,
            @Valid @RequestBody PropertyRequestDTO dto,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, UserNotFoundException, InvalidOperationException {
        Property updatedProperty = PropertyMapper.toProperty(dto);
        Property savedProperty = propertyService.updateProperty(propertyId, updatedProperty, authenticatedUser.getId());
        return ResponseEntity.ok(PropertyMapper.toPropertyResponseDTO(savedProperty));
    }

    @DeleteMapping("/{propertyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteProperty(
            @PathVariable UUID propertyId,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException {
        propertyService.deleteProperty(propertyId, authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }

    /**
     * URL: POST /api/properties/{propertyId}/images
     * Adiciona uma imagem a uma propriedade. Apenas owner, agent ou ADMIN.
     * @param propertyId ID da propriedade.
     * @param file Arquivo da imagem.
     * @param isMainImage Se a imagem será a principal.
     * @param authenticatedUser Usuário autenticado.
     * @return PropertyImageDTO da imagem adicionada.
     * @throws ResourceNotFoundException Se a propriedade não for encontrada.
     * @throws UnauthorizedOperationException Se o usuário não tem permissão.
     * @throws FileUploadException, InvalidFileFormatException, FileSizeExceededException Em caso de erro no upload.
     */
    @PostMapping("/{propertyId}/images")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PropertyImageDTO> addPropertyImage(
            @PathVariable UUID propertyId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("isMainImage") boolean isMainImage,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException, InvalidFileFormatException, FileSizeExceededException {
        PropertyImage newImage = propertyService.addPropertyImage(propertyId, file, isMainImage, authenticatedUser.getId());
        return new ResponseEntity<>(PropertyMapper.toPropertyImageDTO(newImage), HttpStatus.CREATED);
    }

    /**
     * URL: DELETE /api/properties/images/{imageId}
     * Deleta uma imagem de propriedade. Apenas owner, agent ou ADMIN.
     * @param imageId ID da imagem.
     * @param authenticatedUser Usuário autenticado.
     * @return ResponseEntity vazia (204 No Content).
     * @throws ResourceNotFoundException Se a imagem não for encontrada.
     * @throws UnauthorizedOperationException Se o usuário não tem permissão.
     */
    @DeleteMapping("/images/{imageId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePropertyImage(
            @PathVariable UUID imageId,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException {
        propertyService.deletePropertyImage(imageId, authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }

    /**
     * URL: PUT /api/properties/{propertyId}/images/{imageId}/main
     * Define uma imagem como principal para uma propriedade. Apenas owner, agent ou ADMIN.
     * @param propertyId ID da propriedade.
     * @param imageId ID da imagem a ser definida como principal.
     * @param authenticatedUser Usuário autenticado.
     * @return PropertyImageDTO da imagem que agora é principal.
     * @throws ResourceNotFoundException Se a propriedade ou imagem não forem encontradas.
     * @throws UnauthorizedOperationException Se o usuário não tem permissão.
     * @throws InvalidOperationException Se a imagem não pertence à propriedade.
     */
    @PutMapping("/{propertyId}/images/{imageId}/main")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PropertyImageDTO> setMainPropertyImage(
            @PathVariable UUID propertyId,
            @PathVariable UUID imageId,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException {
        PropertyImage mainImage = propertyService.setMainPropertyImage(propertyId, imageId, authenticatedUser.getId());
        return ResponseEntity.ok(PropertyMapper.toPropertyImageDTO(mainImage));
    }

    /**
     * URL: PUT /api/properties/{propertyId}/status
     * Atualiza o status de verificação de uma propriedade. Apenas ADMIN ou MODERADOR.
     * @param propertyId ID da propriedade.
     * @param status Novo status de verificação (APPROVED, REJECTED, PENDING).
     * @param rejectionReason Motivo da rejeição (obrigatório se status for REJECTED).
     * @param authenticatedUser Usuário autenticado (ADMIN ou MODERADOR).
     * @return PropertyResponseDTO da propriedade atualizada.
     * @throws ResourceNotFoundException Se a propriedade não for encontrada.
     * @throws UnauthorizedOperationException Se o usuário não tem permissão.
     * @throws InvalidOperationException Se a lógica de negócio impedir (ex: rejeitar sem motivo).
     */
    @PutMapping("/{propertyId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<PropertyResponseDTO> updatePropertyVerificationStatus(
            @PathVariable UUID propertyId,
            @RequestParam("status") PropertyAccountVerificationStatus status,
            @RequestParam(value = "rejectionReason", required = false) String rejectionReason,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException {
        Property updatedProperty = propertyService.updatePropertyVerificationStatus(propertyId, status, rejectionReason, authenticatedUser.getId());
        return ResponseEntity.ok(PropertyMapper.toPropertyResponseDTO(updatedProperty));
    }
}