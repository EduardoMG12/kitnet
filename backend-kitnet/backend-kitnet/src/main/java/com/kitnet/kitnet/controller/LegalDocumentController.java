package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.legalDocument.LegalDocumentRequestDTO;
import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.service.LegalDocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/legal-documents") // Alterado para /api/legal-documents para rotas públicas/gerais
public class LegalDocumentController {

    @Autowired
    private LegalDocumentService legalDocumentService;

    /**
     * Endpoint para administradores adicionarem um novo documento legal ou atualizarem um existente.
     * Se o documento for marcado como ativo, a versão anterior ativa do mesmo tipo será desativada.
     * Requer a role ADMIN.
     * @param dto O DTO contendo os dados do documento legal.
     * @return ResponseEntity com o documento legal salvo e status HTTP 201 (Created) ou 200 (OK).
     */
    @PostMapping("/admin") // Rota específica para ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LegalDocument> createLegalDocument(@Valid @RequestBody LegalDocumentRequestDTO dto) {
        LegalDocument legalDocument = new LegalDocument();
        legalDocument.setType(dto.getType());
        legalDocument.setVersion(dto.getVersion());
        legalDocument.setContent(dto.getContent());
        legalDocument.setEffectiveDate(dto.getEffectiveDate());
        legalDocument.setIsActive(dto.getIsActive());

        LegalDocument savedDocument = legalDocumentService.saveLegalDocument(legalDocument);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obter a versão ativa de um documento legal por tipo.
     * Não requer autenticação, pois os termos ativos são públicos.
     * @param type O tipo do documento legal (e.g., TERMS_OF_USE, LGPD_TERMS, PRIVACY_POLICY).
     * @return ResponseEntity com o documento legal ativo e status HTTP 200 (OK).
     */
    @GetMapping("/active/{type}")
    public ResponseEntity<LegalDocument> getActiveLegalDocument(@PathVariable LegalDocumentType type) {
        LegalDocument activeDocument = legalDocumentService.getActiveLegalDocument(type);
        return ResponseEntity.ok(activeDocument);
    }

    /**
     * Endpoint para obter um documento legal por tipo e versão.
     * Não requer autenticação, pois versões específicas podem ser referenciadas publicamente.
     * @param type O tipo do documento legal.
     * @param version A versão específica do documento.
     * @return ResponseEntity com o documento legal e status HTTP 200 (OK).
     */
    @GetMapping("/{type}/version/{version}")
    public ResponseEntity<LegalDocument> getLegalDocumentByVersion(@PathVariable LegalDocumentType type, @PathVariable String version) {
        LegalDocument document = legalDocumentService.getLegalDocumentByVersion(type, version);
        return ResponseEntity.ok(document);
    }

    /**
     * Endpoint para obter todos os documentos legais (incluindo inativos).
     * Requer a role ADMIN, pois expõe todas as versões.
     * @return ResponseEntity com uma lista de todos os documentos legais e status HTTP 200 (OK).
     */
    @GetMapping("/admin") // Rota específica para ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LegalDocument>> getAllLegalDocuments() {
        List<LegalDocument> documents = legalDocumentService.getAllLegalDocuments();
        return ResponseEntity.ok(documents);
    }

    /**
     * NOVO ENDPOINT: Retorna uma lista de todos os documentos legais que estão atualmente ativos.
     * Não requer autenticação, pois os termos ativos são públicos.
     * @return ResponseEntity com uma lista de LegalDocument ativos e status HTTP 200 (OK).
     */
    @GetMapping("/active")
    public ResponseEntity<List<LegalDocument>> getAllActiveLegalDocuments() {
        List<LegalDocument> activeDocuments = legalDocumentService.getAllActiveLegalDocuments();
        return ResponseEntity.ok(activeDocuments);
    }
}
