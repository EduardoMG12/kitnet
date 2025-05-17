package com.kitnet.kitnet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User buyer;

    @ManyToOne(optional = false)
    private Property property;

    private LocalDate saleDate;

    private Double saleValue;
}
