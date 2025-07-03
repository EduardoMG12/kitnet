package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.ListingModerationStatus;
import com.kitnet.kitnet.model.enums.ListingVisibility;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "property_listings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"property", "lister", "template"}) // Exclui relações bidirecionais
public class PropertyListing {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lister_id", nullable = false)
    private User lister;

    @NotBlank(message = "O título da listagem não pode estar em branco")
    @Size(max = 200, message = "O título da listagem deve ter no máximo 200 caracteres")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "A descrição da listagem não pode estar em branco")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Size(max = 500, message = "A URL da imagem principal deve ter no máximo 500 caracteres")
    @Column(name = "main_image_url", length = 500)
    private String mainImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_template_id", nullable = true)
    private PropertyTemplate template;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListingVisibility visibility;

    @Size(max = 255, message = "O link de acesso deve ter no máximo 255 caracteres")
    @Column(name = "access_link", unique = true, length = 255)
    private String accessLink;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", nullable = false)
    private ListingModerationStatus moderationStatus = ListingModerationStatus.PENDING;

    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;

    @NotNull
    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}