package com.kitnet.kitnet.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PropertyResponseDto {
    private Long id;
    private String propertyType;
    private String adTitle;
    private String description;
    private String purpose;
    private Double rentValue;
    private String city;
    private String state;
    private Boolean ownerConfirmation;
}
