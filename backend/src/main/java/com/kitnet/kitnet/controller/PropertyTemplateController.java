package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.property.PropertyTemplateRequestDTO;
import com.kitnet.kitnet.dto.property.PropertyTemplateResponseDTO;
import com.kitnet.kitnet.mapper.PropertyTemplateMapper;
import com.kitnet.kitnet.model.PropertyTemplate;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.PropertyTemplateTargetRole;
import com.kitnet.kitnet.service.PropertyTemplateService;
import com.kitnet.kitnet.exception.InvalidOperationException;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import com.kitnet.kitnet.exception.UnauthorizedOperationException;
import com.kitnet.kitnet.exception.UserNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/property-templates")
public class PropertyTemplateController {

    @Autowired
    private PropertyTemplateService propertyTemplateService;

    // --- Rotas de ADMIN (Gerenciamento de Templates) ---

    /**
     * URL: POST /api/property-templates/admin
     * Cria um novo template de propriedade. Apenas ADMINs podem criar.
     * @param dto PropertyTemplateRequestDTO com os dados do template.
     * @param authenticatedUser Usuário autenticado (ADMIN).
     * @return PropertyTemplateResponseDTO do template criado.
     * @throws InvalidOperationException Se o nome do template já existe ou validação falha.
     * @throws UnauthorizedOperationException Se o usuário não for ADMIN.
     */
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PropertyTemplateResponseDTO> createPropertyTemplate(
            @Valid @RequestBody PropertyTemplateRequestDTO dto,
            @AuthenticationPrincipal User authenticatedUser) throws InvalidOperationException, UnauthorizedOperationException, UserNotFoundException {
        PropertyTemplate template = PropertyTemplateMapper.toPropertyTemplate(dto); // DTO para Entidade
        PropertyTemplate createdTemplate = propertyTemplateService.createPropertyTemplate(template, authenticatedUser.getId());
        return new ResponseEntity<>(PropertyTemplateMapper.toPropertyTemplateResponseDTO(createdTemplate), HttpStatus.CREATED);
    }

    /**
     * URL: PUT /api/property-templates/admin/{templateId}
     * Atualiza um template de propriedade existente. Apenas ADMINs podem atualizar.
     * @param templateId ID do template a ser atualizado.
     * @param dto PropertyTemplateRequestDTO com os dados atualizados.
     * @param authenticatedUser Usuário autenticado (ADMIN).
     * @return PropertyTemplateResponseDTO do template atualizado.
     * @throws ResourceNotFoundException Se o template não for encontrado.
     * @throws InvalidOperationException Se o nome do template já existe (em outro template) ou validação falha.
     * @throws UnauthorizedOperationException Se o usuário não for ADMIN.
     */
    @PutMapping("/admin/{templateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PropertyTemplateResponseDTO> updatePropertyTemplate(
            @PathVariable UUID templateId,
            @Valid @RequestBody PropertyTemplateRequestDTO dto,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException, UserNotFoundException {
        PropertyTemplate template = PropertyTemplateMapper.toPropertyTemplate(dto); // DTO para Entidade
        PropertyTemplate updatedTemplate = propertyTemplateService.updatePropertyTemplate(templateId, template, authenticatedUser.getId());
        return ResponseEntity.ok(PropertyTemplateMapper.toPropertyTemplateResponseDTO(updatedTemplate));
    }

    /**
     * URL: DELETE /api/property-templates/admin/{templateId}
     * Deleta um template de propriedade. Apenas ADMINs podem deletar.
     * @param templateId ID do template a ser deletado.
     * @param authenticatedUser Usuário autenticado (ADMIN).
     * @return ResponseEntity vazia (204 No Content).
     * @throws ResourceNotFoundException Se o template não for encontrado.
     * @throws UnauthorizedOperationException Se o usuário não for ADMIN.
     */
    @DeleteMapping("/admin/{templateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePropertyTemplate(
            @PathVariable UUID templateId,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, UserNotFoundException {
        propertyTemplateService.deletePropertyTemplate(templateId, authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }

    /**
     * URL: PUT /api/property-templates/admin/{templateId}/activate
     * Ativa um template de propriedade. Apenas ADMINs podem ativar.
     * @param templateId ID do template a ser ativado.
     * @param authenticatedUser Usuário autenticado (ADMIN).
     * @return PropertyTemplateResponseDTO do template ativado.
     * @throws ResourceNotFoundException Se o template não for encontrado.
     * @throws UnauthorizedOperationException Se o usuário não for ADMIN.
     * @throws InvalidOperationException Se o template já estiver ativo.
     */
    @PutMapping("/admin/{templateId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PropertyTemplateResponseDTO> activatePropertyTemplate(
            @PathVariable UUID templateId,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException, UserNotFoundException {
        PropertyTemplate activatedTemplate = propertyTemplateService.activatePropertyTemplate(templateId, authenticatedUser.getId());
        return ResponseEntity.ok(PropertyTemplateMapper.toPropertyTemplateResponseDTO(activatedTemplate));
    }

    /**
     * URL: PUT /api/property-templates/admin/{templateId}/deactivate
     * Desativa um template de propriedade. Apenas ADMINs podem desativar.
     * @param templateId ID do template a ser desativado.
     * @param authenticatedUser Usuário autenticado (ADMIN).
     * @return PropertyTemplateResponseDTO do template desativado.
     * @throws ResourceNotFoundException Se o template não for encontrado.
     * @throws UnauthorizedOperationException Se o usuário não for ADMIN.
     * @throws InvalidOperationException Se o template já estiver inativo.
     */
    @PutMapping("/admin/{templateId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PropertyTemplateResponseDTO> deactivatePropertyTemplate(
            @PathVariable UUID templateId,
            @AuthenticationPrincipal User authenticatedUser) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException, UserNotFoundException {
        PropertyTemplate deactivatedTemplate = propertyTemplateService.deactivatePropertyTemplate(templateId, authenticatedUser.getId());
        return ResponseEntity.ok(PropertyTemplateMapper.toPropertyTemplateResponseDTO(deactivatedTemplate));
    }


    // --- Rotas Públicas (Consulta de Templates) ---

    /**
     * URL: GET /api/property-templates/{templateId}
     * Obtém um template de propriedade pelo ID. Público.
     * @param templateId ID do template.
     * @return PropertyTemplateResponseDTO.
     * @throws ResourceNotFoundException Se o template não for encontrado.
     */
    @GetMapping("/{templateId}")
    public ResponseEntity<PropertyTemplateResponseDTO> getPropertyTemplateById(@PathVariable UUID templateId) throws ResourceNotFoundException {
        PropertyTemplate template = propertyTemplateService.getPropertyTemplateById(templateId);
        return ResponseEntity.ok(PropertyTemplateMapper.toPropertyTemplateResponseDTO(template));
    }

    /**
     * URL: GET /api/property-templates/name/{templateName}
     * Obtém um template de propriedade pelo nome. Público.
     * @param templateName Nome do template.
     * @return PropertyTemplateResponseDTO.
     * @throws ResourceNotFoundException Se o template não for encontrado.
     */
    @GetMapping("/name/{templateName}")
    public ResponseEntity<PropertyTemplateResponseDTO> getPropertyTemplateByName(@PathVariable String templateName) throws ResourceNotFoundException {
        PropertyTemplate template = propertyTemplateService.getPropertyTemplateByName(templateName);
        return ResponseEntity.ok(PropertyTemplateMapper.toPropertyTemplateResponseDTO(template));
    }

    /**
     * URL: GET /api/property-templates/active
     * Lista todos os templates ativos (públicos e não públicos). Público.
     * @return Lista de PropertyTemplateResponseDTO.
     */
    @GetMapping("/active")
    public ResponseEntity<List<PropertyTemplateResponseDTO>> getAllActiveTemplates() {
        List<PropertyTemplate> templates = propertyTemplateService.getAllActiveTemplates();
        return ResponseEntity.ok(PropertyTemplateMapper.toPropertyTemplateResponseDTOList(templates));
    }

    /**
     * URL: GET /api/property-templates/active/role/{targetRole}
     * Lista templates ativos filtrados por targetRole. Público.
     * @param targetRole Role alvo (LESSOR, REAL_ESTATE_AGENT, ALL).
     * @return Lista de PropertyTemplateResponseDTO.
     */
    @GetMapping("/active/role/{targetRole}")
    public ResponseEntity<List<PropertyTemplateResponseDTO>> getActiveTemplatesByTargetRole(@PathVariable PropertyTemplateTargetRole targetRole) {
        List<PropertyTemplate> templates = propertyTemplateService.getActiveTemplatesByTargetRole(targetRole);
        return ResponseEntity.ok(PropertyTemplateMapper.toPropertyTemplateResponseDTOList(templates));
    }
}