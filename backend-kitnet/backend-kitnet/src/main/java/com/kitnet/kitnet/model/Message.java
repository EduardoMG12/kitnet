package com.kitnet.kitnet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // O ID da mensagem pode continuar sendo Long se preferir

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false) // Garante que a coluna referencie o UUID do User
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false) // Garante que a coluna referencie o UUID do User
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY) // Assumindo que Property ainda usa Long como ID
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime sentAt;
}
