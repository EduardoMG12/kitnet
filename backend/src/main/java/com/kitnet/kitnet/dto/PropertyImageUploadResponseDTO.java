package com.kitnet.kitnet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyImageUploadResponseDTO {
    private String message;
    private String imageUrl;
    private String imageId;
    private String propertyId;
}