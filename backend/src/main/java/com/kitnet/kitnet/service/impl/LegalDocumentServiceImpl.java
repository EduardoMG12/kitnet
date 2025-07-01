package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.UserLegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.repository.LegalDocumentRepository;
import com.kitnet.kitnet.repository.UserLegalDocumentRepository;
import com.kitnet.kitnet.service.LegalDocumentService;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import com.kitnet.kitnet.exception.InvalidOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class LegalDocumentServiceImpl implements LegalDocumentService {

    @Autowired
    private LegalDocumentRepository legalDocumentRepository;
    @Autowired
    private UserLegalDocumentRepository userLegalDocumentRepository;
    @Autowired
    private MessageSource messageSource;

    // --- Methods manage Global of Terms (ADMIN) ---

    @Override
    @Transactional
    public LegalDocument createOrUpdateLegalDocument(LegalDocument legalDocument) throws InvalidOperationException {
        // Regra de Negócio: Não pode ter a mesma versão do termo de uso do mesmo tipo
        Optional<LegalDocument> existingSpecificVersion = legalDocumentRepository.findByTypeAndVersion(legalDocument.getType(), legalDocument.getVersion());

        // Verifica se já existe um documento com o mesmo tipo E versão
        // E se o ID do documento que estamos tentando salvar é nulo (nova criação)
        // OU se o ID do documento que estamos tentando salvar é diferente do existente (atualização de outro ID para essa mesma versão)
        if (existingSpecificVersion.isPresent() && (legalDocument.getId() == null || !legalDocument.getId().equals(existingSpecificVersion.get().getId()))) {
            throw new InvalidOperationException(messageSource.getMessage("error.legal.document.version.exists", new Object[]{legalDocument.getType().name(), legalDocument.getVersion()}, LocaleContextHolder.getLocale()));
        }

        // 1. Desativar todas as outras versões ativas do mesmo tipo, EXCETO a que estamos salvando/ativando
        if (legalDocument.getIsActive()) {
            legalDocumentRepository.findAllByTypeAndIsActiveTrue(legalDocument.getType())
                    .forEach(activeDoc -> {
                        // Garante que não desativa o próprio documento que está sendo processado
                        if (!activeDoc.getId().equals(legalDocument.getId())) {
                            activeDoc.setIsActive(false);
                            legalDocumentRepository.save(activeDoc); // Persiste a desativação
                            System.out.println("Legal Document " + activeDoc.getType().name() + " version " + activeDoc.getVersion() + " deactivated globally.");
                        }
                    });
        }

        // 2. Salvar ou atualizar o documento legal
        LegalDocument savedDocument = legalDocumentRepository.save(legalDocument);
        System.out.println("Legal Document " + savedDocument.getType().name() + " version " + savedDocument.getVersion() + " saved/updated. Active: " + savedDocument.getIsActive());

        return savedDocument;
    }

    @Override
    @Transactional(readOnly = true)
    public LegalDocument getActiveLegalDocument(LegalDocumentType type) throws ResourceNotFoundException {
        return legalDocumentRepository.findByTypeAndIsActiveTrue(type)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.legal.document.not.found.active", new Object[]{type.name()}, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public LegalDocument getLegalDocumentByVersion(LegalDocumentType type, String version) throws ResourceNotFoundException {
        return legalDocumentRepository.findByTypeAndVersion(type, version)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.legal.document.not.found.version", new Object[]{type.name(), version}, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LegalDocument> getAllLegalDocuments() {
        return legalDocumentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LegalDocument> getAllActiveLegalDocuments() {
        // Este método já usa findByIsActiveTrue(), que deveria retornar apenas os 3 ativos.
        return legalDocumentRepository.findByIsActiveTrue();
    }

    // --- Métodos de Aceitação e Verificação de Termos por Usuário ---

    @Override
    @Transactional
    public List<UserLegalDocument> acceptActiveLegalDocumentsForUser(User user, Set<LegalDocumentType> acceptedTypes) throws ResourceNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        List<UserLegalDocument> updatedAcceptances = new ArrayList<>();

        for (LegalDocumentType type : acceptedTypes) {
            LegalDocument activeLegalDocument = getActiveLegalDocument(type); // Busca o termo ativo globalmente

            // 1. Encontra a aceitação ATUAL do usuário para este TIPO de termo
            Optional<UserLegalDocument> existingCurrentUserLegalDocOpt = userLegalDocumentRepository.findByUserAndTypeAndIsCurrentOfUserTrue(user, type); // NOVO MÉTODO NO REPOSITORY

            UserLegalDocument userLegalDoc;
            if (existingCurrentUserLegalDocOpt.isPresent()) {
                userLegalDoc = existingCurrentUserLegalDocOpt.get();
                // Se o usuário já aceitou esta EXATA versão e está como current, nada a fazer
                if (userLegalDoc.getLegalDocument().getId().equals(activeLegalDocument.getId())) {
                    // Já é a versão mais atual aceita pelo usuário. Apenas adiciona à lista e continua.
                    updatedAcceptances.add(userLegalDoc);
                    continue; // Pula para o próximo tipo
                } else {
                    // Usuário aceitou uma versão antiga (ou diferente). Marca a antiga como não atual.
                    userLegalDoc.setIsCurrentOfUser(false);
                    userLegalDocumentRepository.save(userLegalDoc); // Salva a aceitação antiga desativada
                    System.out.println("User " + user.getId() + " acceptance for " + type.name() + " version " + userLegalDoc.getLegalDocument().getVersion() + " deactivated as old version.");
                }
            }

            // 2. Cria uma NOVA aceitação para a versão ATIVA global
            userLegalDoc = UserLegalDocument.builder()
                    .user(user)
                    .legalDocument(activeLegalDocument) // Termo globalmente ativo
                    .type(type)
                    .acceptanceDate(LocalDate.now())
                    .isCurrentOfUser(true) // Marca como aceitação atual do usuário
                    .build();

            updatedAcceptances.add(userLegalDocumentRepository.save(userLegalDoc)); // Salva e adiciona à lista de retorno
            System.out.println("User " + user.getId() + " accepted " + type.name() + " version " + activeLegalDocument.getVersion() + " as current.");
        }
        return updatedAcceptances;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasAcceptedLatestLegalDocument(User user, LegalDocumentType type) {
        // 1. Pega o documento legal ativo mais recente globalmente
        Optional<LegalDocument> latestActiveDocOpt = legalDocumentRepository.findByTypeAndIsActiveTrue(type);

        // Se não há um documento ativo desse tipo, o usuário não precisa aceitar nada.
        if (latestActiveDocOpt.isEmpty()) {
            return true;
        }

        LegalDocument latestActiveDoc = latestActiveDocOpt.get();

        // 2. Verifica se o usuário tem um UserLegalDocument que é isCurrentOfUser=true
        // E se o LegalDocument desse UserLegalDocument é o MESMO que o latestActiveDoc
        return user.getUserLegalDocuments().stream()
                .filter(uld -> uld.getType() == type) // Filtra pelo tipo
                .filter(UserLegalDocument::getIsCurrentOfUser) // Apenas a aceitação que o usuário considera 'atual'
                .anyMatch(uld -> uld.getLegalDocument().getId().equals(latestActiveDoc.getId())); // Compara IDs
    }

    @Override
    @Transactional(readOnly = true)
    public boolean needsToRevalidateLegalDocuments(User user) {
        // Itera sobre TODOS os tipos de LegalDocumentType para verificar se ALGUM precisa de revalidação
        for (LegalDocumentType type : LegalDocumentType.values()) { // OU apenas os tipos críticos, se preferir
            // Se o usuário não aceitou a versão mais recente de um tipo, precisa revalidar.
            if (!hasAcceptedLatestLegalDocument(user, type)) {
                return true; // Encontrou um termo que precisa de revalidação
            }
        }
        return false; // Nenhum termo precisa de revalidação
    }
}