package com.kitnet.kitnet.mapper;

import com.kitnet.kitnet.dto.property.PropertyTemplateRequestDTO;
import com.kitnet.kitnet.dto.property.PropertyTemplateResponseDTO;
import com.kitnet.kitnet.model.PropertyTemplate;

import java.util.ArrayList; // Usar ArrayList em vez de List.of() para mutabilidade se necessário
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Removidas anotações do MapStruct
public class PropertyTemplateMapper {

    public static PropertyTemplate toPropertyTemplate(PropertyTemplateRequestDTO dto) {
        if (dto == null) return null;
        PropertyTemplate template = new PropertyTemplate();
        // ID, createdAt, updatedAt serão ignorados e setados automaticamente ou no serviço
        template.setTemplateName(dto.getTemplateName());
        template.setDescription(dto.getDescription());
        template.setTargetRole(dto.getTargetRole());
        template.setIsPremium(dto.getIsPremium());
        template.setIsActive(dto.getIsActive());
        return template;
    }

    public static PropertyTemplateResponseDTO toPropertyTemplateResponseDTO(PropertyTemplate template) {
        if (template == null) return null;
        PropertyTemplateResponseDTO dto = new PropertyTemplateResponseDTO();
        dto.setId(template.getId());
        dto.setTemplateName(template.getTemplateName());
        dto.setDescription(template.getDescription());
        dto.setTargetRole(template.getTargetRole());
        dto.setIsPremium(template.getIsPremium());
        dto.setIsActive(template.getIsActive());
        dto.setCreatedAt(template.getCreatedAt());
        dto.setUpdatedAt(template.getUpdatedAt());
        return dto;
    }

    public static List<PropertyTemplateResponseDTO> toPropertyTemplateResponseDTOList(List<PropertyTemplate> templates) {
        if (templates == null) return new ArrayList<>(); // Retorna lista vazia
        return templates.stream()
                .map(PropertyTemplateMapper::toPropertyTemplateResponseDTO)
                .collect(Collectors.toList());
    }
}