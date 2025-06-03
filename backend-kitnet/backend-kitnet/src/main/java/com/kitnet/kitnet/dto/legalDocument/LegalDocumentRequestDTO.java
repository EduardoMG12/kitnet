package com.kitnet.kitnet.dto.legalDocument;

import com.kitnet.kitnet.model.enums.LegalDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LegalDocumentRequestDTO {

    @NotNull(message = "O tipo do documento legal não pode ser nulo")
    private LegalDocumentType type; // E.g: TERMS_OF_USE, LGPD_TERMS, PRIVACY_POLICY

    @NotBlank(message = "A versão do documento não pode estar em branco")
    @Size(max = 50, message = "A versão deve ter no máximo 50 caracteres")
    private String version;

    @NotBlank(message = "O conteúdo do documento não pode estar em branco")
    private String content;

    @NotNull(message = "A data de efetivação não pode ser nula")
    private LocalDate effectiveDate;

    @NotNull(message = "O status de ativo não pode ser nulo")
    private Boolean isActive;
}
