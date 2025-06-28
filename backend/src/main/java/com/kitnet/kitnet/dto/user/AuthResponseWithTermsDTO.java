package com.kitnet.kitnet.dto.user;

import com.kitnet.kitnet.dto.legalDocument.LegalDocumentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseWithTermsDTO {
    private UserSimpleResponseDTO user;
    private List<LegalDocumentDTO> allAcceptedLegalDocuments;
    private String jwtToken;
}