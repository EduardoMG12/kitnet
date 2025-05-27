package com.kitnet.kitnet.dto.rental;

import lombok.Data;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class RentalUpdateDTO {
    @Positive(message = "O valor do aluguel mensal proposto deve ser positivo.")
    private Double proposedMonthlyRent; // Campo para a nova proposta de preço

    @FutureOrPresent(message = "A data de início da alteração deve ser hoje ou no futuro.")
    private LocalDate effectiveDate; // Data a partir da qual a alteração de preço ou término entraria em vigor

    // Outros campos que podem ser atualizados ou para ações de contrato
    private String action; // Ex: "PROPOSE_PRICE_CHANGE", "APPROVE_PRICE_CHANGE", "REJECT_PRICE_CHANGE", "TERMINATE_CONTRACT", "BREAK_CONTRACT"
}