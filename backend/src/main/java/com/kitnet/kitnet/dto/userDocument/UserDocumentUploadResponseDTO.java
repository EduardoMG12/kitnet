package com.kitnet.kitnet.dto.userDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentUploadResponseDTO {
    private String documentId;
    private String documentType;
    private String documentUrl;
    private String userId;
}