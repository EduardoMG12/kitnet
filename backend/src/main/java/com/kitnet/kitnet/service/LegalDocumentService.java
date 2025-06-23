package com.kitnet.kitnet.service;

import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface LegalDocumentService {
    LegalDocument getActiveLegalDocument(LegalDocumentType type) throws ResourceNotFoundException;
    LegalDocument getLegalDocumentByVersion(LegalDocumentType type, String version) throws ResourceNotFoundException;
    LegalDocument saveLegalDocument(LegalDocument legalDocument);
    List<LegalDocument> getAllLegalDocuments();
    List<LegalDocument> getAllActiveLegalDocuments();
}
