package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.PropertyPurpose;
import com.kitnet.kitnet.model.enums.PropertyType;
import com.kitnet.kitnet.model.enums.PropertyAccountVerificationStatus;
import com.kitnet.kitnet.model.enums.AnnouncingUserRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"owner", "agent", "images"})
public class Property {

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
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = true)
    private User agent;

    @NotBlank(message = "O título não pode estar em branco")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "A descrição não pode estar em branco")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "O tipo do imóvel é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType type;

    @NotNull(message = "A finalidade do imóvel é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyPurpose purpose;

    @DecimalMin(value = "0.00", message = "O preço de aluguel deve ser maior ou igual a zero")
    @Column(name = "rental_price", precision = 10, scale = 2)
    private BigDecimal rentalPrice;

    @DecimalMin(value = "0.00", message = "O preço de venda deve ser maior ou igual a zero")
    @Column(name = "sale_price", precision = 10, scale = 2)
    private BigDecimal salePrice;

    @NotNull
    @Column(name = "show_rental_price", nullable = false)
    private Boolean showRentalPrice = true;

    @NotNull
    @Column(name = "show_sale_price", nullable = false)
    private Boolean showSalePrice = true;

    @Size(max = 8, message = "O CEP deve ter 8 dígitos")
    @Column(name = "zip_code", length = 8)
    private String zipCode;

    @Size(max = 100, message = "O estado deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String state;

    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String city;

    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String neighborhood;

    @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    @Column(name = "address_street", length = 255)
    private String addressStreet;

    @Size(max = 255, message = "O complemento do endereço deve ter no máximo 255 caracteres")
    @Column(name = "address_complement", length = 255)
    private String addressComplement;

    @NotNull
    @Column(name = "hide_exact_address", nullable = false)
    private Boolean hideExactAddress = false;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @DecimalMin(value = "0.00", message = "A área total deve ser maior ou igual a zero")
    @Column(name = "square_meters", precision = 10, scale = 2)
    private BigDecimal squareMeters;

    @DecimalMin(value = "0.00", message = "A área construída deve ser maior ou igual a zero")
    @Column(name = "built_area", precision = 10, scale = 2)
    private BigDecimal builtArea;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    @Column(name = "parking_spaces")
    private Integer parkingSpaces;

    @NotNull
    @Column(name = "accepts_pets", nullable = false)
    private Boolean acceptsPets = false;

    @Column(columnDefinition = "TEXT")
    private String amenities;

    @Column(name = "floor")
    private Integer floor;

    @DecimalMin(value = "0.00", message = "A taxa de condomínio deve ser maior ou igual a zero")
    @Column(name = "condominium_fee", precision = 10, scale = 2)
    private BigDecimal condominiumFee;

    @NotNull
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @NotNull
    @Column(name = "owner_confirmation", nullable = false)
    private Boolean ownerConfirmation = false;

    @NotNull
    @Column(name = "terms_agreement", nullable = false)
    private Boolean termsAgreement;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_verification_status", nullable = false)
    private PropertyAccountVerificationStatus accountVerificationStatus = PropertyAccountVerificationStatus.NOT_SUBMITTED;

    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "announcing_user_role", nullable = false)
    private AnnouncingUserRole announcingUserRole;

    @NotNull
    @Column(name = "allow_other_agents", nullable = false)
    private Boolean allowOtherAgents = false;

    @Column(name = "max_allowed_agents", nullable = false)
    private Integer maxAllowedAgents = 0; // 0 = ilimitado

    @Column(name = "brokerage_fee_percentage", precision = 5, scale = 2)
    private BigDecimal brokerageFeePercentage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // --- Relacionamentos OneToMany ---
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PropertyImage> images = new HashSet<>();

    // Opcional: Relacionamento com Template se cada propriedade tiver um template fixo
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "template_id", nullable = true)
    // private PropertyTemplate template;
}