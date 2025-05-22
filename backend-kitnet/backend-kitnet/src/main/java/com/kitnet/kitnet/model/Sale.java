package com.kitnet.kitnet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // O ID da venda pode continuar sendo Long se preferir

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false) // Garante que a coluna referencie o UUID do User
    private User buyer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) // Assumindo que Property ainda usa Long como ID
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    private LocalDate saleDate;

    private Double saleValue;
}
