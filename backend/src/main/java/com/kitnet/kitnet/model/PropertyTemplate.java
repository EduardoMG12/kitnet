package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.PropertyTemplateTargetRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "property_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class PropertyTemplate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @NotBlank(message = "O nome do template não pode estar em branco")
    @Size(max = 100, message = "O nome do template deve ter no máximo 100 caracteres")
    @Column(name = "template_name", nullable = false, unique = true, length = 100)
    private String templateName;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Column(length = 255)
    private String description;

    @NotNull(message = "A role alvo do template é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "target_role", nullable = false)
    private PropertyTemplateTargetRole targetRole;

    @NotNull
    @Column(name = "is_premium", nullable = false)
    private Boolean isPremium = false;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}