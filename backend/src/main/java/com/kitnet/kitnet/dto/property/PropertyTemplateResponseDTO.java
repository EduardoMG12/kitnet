// src/main/java/com/kitnet.kitnet.dto.property/PropertyTemplateResponseDTO.java
package com.kitnet.kitnet.dto.property;

import com.kitnet.kitnet.model.enums.PropertyTemplateTargetRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PropertyTemplateResponseDTO {
    private UUID id;
    private String templateName;
    private String description;
    private PropertyTemplateTargetRole targetRole;
    private Boolean isPremium;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}