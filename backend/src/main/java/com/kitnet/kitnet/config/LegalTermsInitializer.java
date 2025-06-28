package com.kitnet.kitnet.config;

import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.repository.LegalDocumentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Adicionar import

import java.time.LocalDate;
import java.util.List;
import java.util.Optional; // Adicionar import

@Component
@RequiredArgsConstructor
public class LegalTermsInitializer {

    private final LegalDocumentRepository legalDocumentRepository;

    @PostConstruct
    @Transactional
    public void init() {
        createOrUpdateLegalDocument(LegalDocumentType.TERMS_OF_USE, "1.0", "Conteúdo Default dos Termos de Uso");
        createOrUpdateLegalDocument(LegalDocumentType.LGPD_TERMS, "1.1", "Conteúdo Default dos Termos LGPD");
        createOrUpdateLegalDocument(LegalDocumentType.PRIVACY_POLICY, "1.2", "Conteúdo Default da Política de Privacidade");
    }

    private void createOrUpdateLegalDocument(LegalDocumentType type, String version, String content) {
        Optional<LegalDocument> existingSpecificVersion = legalDocumentRepository.findByTypeAndVersion(type, version);

        if (existingSpecificVersion.isPresent()) {
            LegalDocument doc = existingSpecificVersion.get();
                System.out.println("Legal Document " + type.name() + " version " + version + " already exists and is active.");
                return;
        }

        LegalDocument newDoc = new LegalDocument();
        newDoc.setType(type);
        newDoc.setVersion(version);
        newDoc.setContent(content);
        newDoc.setEffectiveDate(LocalDate.now());
        newDoc.setIsActive(true);

        deactivateOtherVersionsAndSave(newDoc);

        legalDocumentRepository.save(newDoc);
        System.out.println("Legal Document " + type.name() + " version " + version + " created/updated and set as active.");
    }

    private void deactivateOtherVersionsAndSave(LegalDocument newActiveDoc) {
        List<LegalDocument> oldDocs = legalDocumentRepository.findAllByTypeAndIsActiveTrue(newActiveDoc.getType());
        oldDocs.forEach(d -> {
            if (!d.getId().equals(newActiveDoc.getId())) {
                d.setIsActive(false);
                legalDocumentRepository.save(d);
            }
        });
    }
}