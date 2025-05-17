package com.kitnet.kitnet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User tenant;

    @ManyToOne(optional = false)
    private Property property;

    private LocalDate startDate;
    private LocalDate endDate;

    private Double monthlyRent;

    private Boolean active;
}
