package com.kitnet.kitnet.mapper;

import com.kitnet.kitnet.dto.legalDocument.LegalDocumentDTO;
import com.kitnet.kitnet.model.LegalDocument;

import java.util.List;
import java.util.stream.Collectors;

public class LegalDocumentMapper {

    public static LegalDocumentDTO toLegalDocumentDTO(LegalDocument legalDocument) {
        if (legalDocument == null) return null;
        return new LegalDocumentDTO(
                legalDocument.getId(),
                legalDocument.getType(),
                legalDocument.getVersion(),
                legalDocument.getContent(),
                legalDocument.getEffectiveDate(),
                legalDocument.getIsActive()
        );
    }

    public static List<LegalDocumentDTO> toLegalDocumentDTOList(List<LegalDocument> legalDocuments) {
        if (legalDocuments == null) return null;
        return legalDocuments.stream()
                .map(LegalDocumentMapper::toLegalDocumentDTO)
                .collect(Collectors.toList());
    }
}
