package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_document_versions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"userDocument"})
public class UserDocumentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_document_id", nullable = false)
    private UserDocument userDocument;

    @Column(nullable = false)
    private String documentUrl;

    @Column(nullable = false)
    private LocalDate uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus;

    private String rejectionReason;

    @Column(nullable = false)
    private boolean isCurrentVersion;
}