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
    private LegalDocumentType type; // Ex: TERMS_OF_USE, LGPD_TERMS

    @Column(nullable = false) // Ex: "1.0", "1.1", "2023-01-01"
    private String version;

    @Lob // Para armazenar textos longos
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // O texto completo dos termos

    @Column(nullable = false)
    private LocalDate effectiveDate; // Data em que esta versão se tornou efetiva

    @Column(nullable = false)
    private Boolean isActive = true; // Indica se esta é a versão atualmente ativa
}
