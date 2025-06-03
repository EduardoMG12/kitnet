package com.kitnet.kitnet.config;

import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.repository.LegalDocumentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LegalTermsInitializer {

    private final LegalDocumentRepository legalDocumentRepository;

    @PostConstruct
    public void init() {
        createOrUpdateLegalDocument(LegalDocumentType.TERMS_OF_USE, "1.0", "Conteúdo dos Termos de Uso");
        createOrUpdateLegalDocument(LegalDocumentType.LGPD_TERMS, "1.1", "Conteúdo dos Termos LGPD");
        createOrUpdateLegalDocument(LegalDocumentType.PRIVACY_POLICY, "1.2", "Conteúdo da Política de Privacidade");
    }

    private void createOrUpdateLegalDocument(LegalDocumentType type, String version, String content) {
        boolean exists = legalDocumentRepository.existsByTypeAndVersion(type, version);

        if (!exists) {
            LegalDocument doc = new LegalDocument();
            doc.setType(type);
            doc.setVersion(version);
            doc.setContent(content);
            doc.setEffectiveDate(LocalDate.now());
            doc.setIsActive(true);

            List<LegalDocument> oldDocs = legalDocumentRepository.findAllByTypeAndIsActiveTrue(type);
            oldDocs.forEach(d -> {
                d.setIsActive(false);
                legalDocumentRepository.save(d);
            });

            legalDocumentRepository.save(doc);
        }
    }
}