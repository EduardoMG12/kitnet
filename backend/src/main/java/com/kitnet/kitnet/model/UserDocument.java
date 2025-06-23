package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.DocumentType;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private String documentUrl;

    @Column(nullable = false)
    private LocalDate uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus;

    private String rejectionReason;
}