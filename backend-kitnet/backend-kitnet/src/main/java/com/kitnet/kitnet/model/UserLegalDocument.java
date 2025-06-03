package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.LegalDocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_legal_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLegalDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_document_id", nullable = false)
    private LegalDocument legalDocument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LegalDocumentType type;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isCurrentOfUser = true; // Se está de acordo com a versão ativa do documento

    @Column(nullable = false)
    private LocalDate acceptanceDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLegalDocument that = (UserLegalDocument) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
