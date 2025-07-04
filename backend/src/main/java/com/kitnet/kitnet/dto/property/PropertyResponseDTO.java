package com.kitnet.kitnet.dto.property;

import com.kitnet.kitnet.dto.user.UserSimpleResponseDTO;
import com.kitnet.kitnet.model.enums.AnnouncingUserRole;
import com.kitnet.kitnet.model.enums.PropertyAccountVerificationStatus;
import com.kitnet.kitnet.model.enums.PropertyPurpose;
import com.kitnet.kitnet.model.enums.PropertyType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PropertyResponseDTO {
    private UUID id;
    private UserSimpleResponseDTO owner;
    private UserSimpleResponseDTO agent;

    private String title;
    private String description;
    private PropertyType type;
    private PropertyPurpose purpose;
    private BigDecimal rentalPrice;
    private BigDecimal salePrice;
    private Boolean showRentalPrice;
    private Boolean showSalePrice;
    private String zipCode;
    private String state;
    private String city;
    private String neighborhood;
    private String addressStreet;
    private String addressComplement;
    private Boolean hideExactAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal squareMeters;
    private BigDecimal builtArea;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer parkingSpaces;
    private Boolean acceptsPets;
    private String amenities;
    private Integer floor;
    private BigDecimal condominiumFee;
    private Boolean isAvailable;
    private Boolean ownerConfirmation;
    private Boolean termsAgreement;
    private PropertyAccountVerificationStatus accountVerificationStatus;
    private String rejectionReason;
    private AnnouncingUserRole announcingUserRole;
    private Boolean allowOtherAgents;
    private Integer maxAllowedAgents;
    private BigDecimal brokerageFeePercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<PropertyImageDTO> images;
}