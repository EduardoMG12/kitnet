package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employment_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentHistory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String companyName;

    @Column(nullable = false, length = 100)
    private String role;

    @Column(nullable = true)
    private Double monthlyGrossSalary;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = true) // Nullable if is your current work
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean isCurrentJob = false;

    @Column(nullable = false)
    private LocalDate recordUploadDate = LocalDate.now(); // Date of it's register update/add

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.NOT_SUBMITTED;

    @Column(length = 255)
    private String rejectionReason; // description what's register is rejected
}
