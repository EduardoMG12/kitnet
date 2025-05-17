package com.kitnet.kitnet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private Property property;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime sentAt;
}
