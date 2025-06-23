package com.kitnet.kitnet.dto;

import com.kitnet.kitnet.model.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDocumentUploadDTO {
    @NotNull(message = "O tipo de documento é obrigatório")
    private DocumentType documentType;
    @NotBlank(message = "A URL do documento é obrigatória")
    private String documentUrl;
}