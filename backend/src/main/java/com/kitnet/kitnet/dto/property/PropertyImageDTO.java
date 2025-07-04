package com.kitnet.kitnet.dto.property;

import lombok.Data;
import java.util.UUID;

@Data
public class PropertyImageDTO {
    private UUID id;
    private UUID propertyId;
    private String imageUrl;
    private Integer orderIndex;
    private Boolean isMainImage;
}