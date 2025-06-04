package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.repository.LegalDocumentRepository;
import com.kitnet.kitnet.service.LegalDocumentService;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LegalDocumentServiceImpl implements LegalDocumentService {

    @Autowired
    private LegalDocumentRepository legalDocumentRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public LegalDocument getActiveLegalDocument(LegalDocumentType type) throws ResourceNotFoundException {
        return legalDocumentRepository.findByTypeAndIsActiveTrue(type)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.legal.document.not.found.active", new Object[]{type.name()}, LocaleContextHolder.getLocale())));
    }

    @Override
    public LegalDocument getLegalDocumentByVersion(LegalDocumentType type, String version) throws ResourceNotFoundException {
        return legalDocumentRepository.findByTypeAndVersion(type, version)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.legal.document.not.found.version", new Object[]{type.name(), version}, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional
    public LegalDocument saveLegalDocument(LegalDocument legalDocument) {
        // Logic to ensure only one version is active per type
        if (legalDocument.getIsActive()) {
            legalDocumentRepository.findByTypeAndIsActiveTrue(legalDocument.getType()).ifPresent(activeDoc -> {
                if (legalDocument.getId() == null || !activeDoc.getId().equals(legalDocument.getId())) {
                    activeDoc.setIsActive(false);
                    legalDocumentRepository.save(activeDoc);
                }
            });
        }
        return legalDocumentRepository.save(legalDocument);
    }

    @Override
    public List<LegalDocument> getAllLegalDocuments() {
        return legalDocumentRepository.findAll();
    }

    @Override
    public List<LegalDocument> getAllActiveLegalDocuments() {
        return legalDocumentRepository.findByIsActiveTrue();
    }
}
