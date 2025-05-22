package com.kitnet.kitnet.dto;

import com.kitnet.kitnet.model.RentalStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RentalResponseDTO {
    private UUID id;
    private UUID tenantId;
    private String tenantEmail;
    private UUID propertyId;
    private String propertyTitle;
    private UUID ownerId;
    private String ownerEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double monthlyRent;
    private Boolean active;
    private RentalStatus status;
    private LocalDate lastPriceUpdateDate;
    private Double proposedMonthlyRent;
}