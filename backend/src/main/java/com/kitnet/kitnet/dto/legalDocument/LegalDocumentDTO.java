package com.kitnet.kitnet.dto.legalDocument;

import com.kitnet.kitnet.model.enums.LegalDocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalDocumentDTO {
    private UUID id;
    private LegalDocumentType type;
    private String version;
    private String content;
    private LocalDate effectiveDate;
    private Boolean isActive;
}