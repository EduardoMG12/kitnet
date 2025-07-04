// src/main/java/com/kitnet.kitnet.dto.property/PropertyTemplateRequestDTO.java
package com.kitnet.kitnet.dto.property;

import com.kitnet.kitnet.model.enums.PropertyTemplateTargetRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PropertyTemplateRequestDTO {
    @NotBlank(message = "O nome do template não pode estar em branco")
    @Size(max = 100, message = "O nome do template deve ter no máximo 100 caracteres")
    private String templateName;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String description;

    @NotNull(message = "A role alvo do template é obrigatória")
    private PropertyTemplateTargetRole targetRole;

    @NotNull(message = "O status premium é obrigatório")
    private Boolean isPremium = false;

    @NotNull(message = "O status ativo é obrigatório")
    private Boolean isActive = true;
}