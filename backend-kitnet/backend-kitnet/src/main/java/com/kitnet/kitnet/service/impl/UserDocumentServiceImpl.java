package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.UserDocumentUploadDTO;
import com.kitnet.kitnet.model.*;
import com.kitnet.kitnet.repository.UserDocumentRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.UserDocumentService;
import com.kitnet.kitnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserDocumentServiceImpl implements UserDocumentService {

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDocument uploadDocument(UUID userId, UserDocumentUploadDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        UserDocument document = new UserDocument();
        document.setUser(user);
        document.setDocumentType(dto.getDocumentType());
        document.setDocumentUrl(dto.getDocumentUrl());
        document.setUploadDate(LocalDate.now());
        document.setVerificationStatus(VerificationStatus.PENDING); // Documento enviado, então pendente de revisão

        // Ao enviar um documento, o status geral do usuário deve ir para PENDING (se não estiver APPROVED)
        if (user.getAccountVerificationStatus() != VerificationStatus.APPROVED &&
                user.getAccountVerificationStatus() != VerificationStatus.PENDING) { // Evita loop ou reset desnecessário
            userService.setVerificationStatus(user.getId(), VerificationStatus.PENDING);
        }

        return userDocumentRepository.save(document);
    }

    @Override
    public List<UserDocument> getDocumentsByUser(UUID userId) {
        return userDocumentRepository.findByUserId(userId);
    }

    @Override
    public UserDocument getDocumentById(UUID documentId) {
        return userDocumentRepository.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Documento não encontrado."));
    }

    @Override
    @Transactional
    public UserDocument setDocumentVerificationStatus(UUID documentId, VerificationStatus status, String rejectionReason, User currentUser) {
        // Check if the current user has either ADMIN or MODERATOR role
        if (!currentUser.hasRole(RoleName.ADMIN) && !currentUser.hasRole(RoleName.MODERATOR)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para realizar esta ação.");
        }

        UserDocument document = userDocumentRepository.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Documento não encontrado."));

        document.setVerificationStatus(status);
        document.setRejectionReason(status == VerificationStatus.REJECTED ? rejectionReason : null);
        UserDocument savedDocument = userDocumentRepository.save(document);

        User user = savedDocument.getUser();

        // Após alterar o status de um documento, reavalie o status geral do usuário
        boolean allRequiredDocsApproved = checkAllRequiredDocumentsApproved(user); // Chamada da função auxiliar

        if (allRequiredDocsApproved) {
            userService.setVerificationStatus(user.getId(), VerificationStatus.APPROVED);
            user.setIsIdentityConfirmed(true);
        } else if (status == VerificationStatus.REJECTED && user.getAccountVerificationStatus() != VerificationStatus.REJECTED) {
            userService.setVerificationStatus(user.getId(), VerificationStatus.PENDING);
            user.setIsIdentityConfirmed(false);
        } else if (status == VerificationStatus.APPROVED && user.getAccountVerificationStatus() == VerificationStatus.NOT_SUBMITTED) {
            userService.setVerificationStatus(user.getId(), VerificationStatus.PENDING);
        }

        return savedDocument;
    }

    @Override
    public List<UserDocument> findDocumentsNeedingReview() {
        return userDocumentRepository.findByVerificationStatus(VerificationStatus.PENDING);
    }

    // Dentro de UserDocumentServiceImpl
    private boolean checkAllRequiredDocumentsApproved(User user) {
        List<UserDocument> userDocs = userDocumentRepository.findByUserId(user.getId());

        // Documentos básicos para identidade (PF e PJ) e selfie
        boolean hasIdDoc = userDocs.stream().anyMatch(d ->
                (d.getDocumentType() == DocumentType.RG_FRENTE ||
                        d.getDocumentType() == DocumentType.RG_VERSO ||
                        d.getDocumentType() == DocumentType.CPF ||
                        d.getDocumentType() == DocumentType.CNH_FRENTE ||
                        d.getDocumentType() == DocumentType.CNH_VERSO ||
                        d.getDocumentType() == DocumentType.PASSAPORTE) &&
                        d.getVerificationStatus() == VerificationStatus.APPROVED
        );
        boolean hasSelfie = userDocs.stream().anyMatch(d ->
                d.getDocumentType() == DocumentType.SELFIE_COM_DOCUMENTO &&
                        d.getVerificationStatus() == VerificationStatus.APPROVED
        );
        boolean hasProofOfAddress = userDocs.stream().anyMatch(d ->
                d.getDocumentType() == DocumentType.COMPROVANTE_RESIDENCIA &&
                        d.getVerificationStatus() == VerificationStatus.APPROVED
        );

        // Verificação de e-mail e telefone
        boolean emailAndPhoneVerified = user.getIsEmailVerified() && user.getIsPhoneVerified();

        // Lógica específica por tipo de pessoa e role
        if (user.getLegalPersonType() == LegalPersonType.PF) {
            // Regras para Pessoas Físicas
            if (!hasIdDoc || !hasSelfie || !hasProofOfAddress || !emailAndPhoneVerified) {
                return false;
            }

            if (user.hasRole(RoleName.LESSEE)) {
                // Para locatário PF, precisa de comprovante de renda
                boolean hasIncomeProof = userDocs.stream().anyMatch(d ->
                        (d.getDocumentType() == DocumentType.COMPROVANTE_RENDA ||
                                d.getDocumentType() == DocumentType.EXTRATO_BANCARIO_ULTIMOS_MESES) &&
                                d.getVerificationStatus() == VerificationStatus.APPROVED
                );
                return hasIncomeProof; // Retorna true se todos os anteriores + renda forem true
            } else if (user.hasRole(RoleName.LESSOR)) {
                // Para locador PF, pode precisar de prova de propriedade do imóvel
                // Ex: boolean hasPropertyProof = ...
                return true; // Assume que os docs básicos são suficientes por enquanto
            } else if (user.hasRole(RoleName.REAL_ESTATE_AGENT)) {
                // Corretor PF precisa de CRECI
                boolean hasCreci = userDocs.stream().anyMatch(d ->
                        d.getDocumentType() == DocumentType.CRECI &&
                                d.getVerificationStatus() == VerificationStatus.APPROVED
                );
                return hasCreci;
            }
            // Se for um usuário PF sem uma role específica (ex: recém-registrado), apenas docs básicos.
            return true;

        } else if (user.getLegalPersonType() == LegalPersonType.PJ) {
            // Regras para Pessoas Jurídicas
            boolean hasCnpj = userDocs.stream().anyMatch(d ->
                    d.getDocumentType() == DocumentType.CNPJ &&
                            d.getVerificationStatus() == VerificationStatus.APPROVED
            );
            boolean hasSocialContract = userDocs.stream().anyMatch(d ->
                    d.getDocumentType() == DocumentType.CONTRATO_SOCIAL &&
                            d.getVerificationStatus() == VerificationStatus.APPROVED
            );
            boolean hasIdDocsForPartners = userDocs.stream().anyMatch(d -> // Lógica para docs de sócios
                    (d.getDocumentType() == DocumentType.RG_FRENTE || d.getDocumentType() == DocumentType.CPF) &&
                            d.getVerificationStatus() == VerificationStatus.APPROVED &&
                            d.getDocumentType().name().startsWith("SOCIO") // Assumindo prefixo para docs de sócios
            ); // Isso pode ser mais complexo, exigindo um relacionamento de "representante"

            if (!hasCnpj || !hasSocialContract || !hasIdDocsForPartners || !hasProofOfAddress || !emailAndPhoneVerified) {
                return false;
            }

            if (user.hasRole(RoleName.LESSOR) || user.hasRole(RoleName.REAL_ESTATE_AGENT)) {
                // Lógica adicional para PJ como locador ou corretor
                return true;
            }
            return false;

        } else if (user.hasRole(RoleName.ADMIN) || user.hasRole(RoleName.MODERATOR) || user.hasRole(RoleName.SUPPORT)) {
            // Para cargos internos, pode ser que exija apenas identidade e email/telefone verificados
            return hasIdDoc && hasSelfie && emailAndPhoneVerified;
        }

        return false; // Se o tipo de pessoa ou role não se encaixa nas regras ou faltam documentos
    }
}