package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.LegalDocumentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "legal_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LegalDocumentType type;

    @Column(nullable = false)
    private String version;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate effectiveDate;

    @Column(nullable = false)
    private Boolean isActive = true;
}
