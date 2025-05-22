package com.kitnet.kitnet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RentalRequestDTO {
    @NotNull(message = "O ID do locatário é obrigatório.")
    private UUID tenantId;

    @NotNull(message = "O ID da propriedade é obrigatório.")
    private UUID propertyId;

    @NotNull(message = "A data de início do aluguel é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser hoje ou no futuro.")
    private LocalDate startDate;

    @NotNull(message = "A data de término do aluguel é obrigatória.")
    private LocalDate endDate;

    @NotNull(message = "O valor do aluguel mensal é obrigatório.")
    private Double monthlyRent;
}