//package com.kitnet.kitnet.service.impl;
//
//import com.kitnet.kitnet.model.enums.DocumentType;
//import com.kitnet.kitnet.model.User;
//import com.kitnet.kitnet.model.UserDocument;
//import com.kitnet.kitnet.model.enums.VerificationStatus;
//import com.kitnet.kitnet.repository.UserDocumentRepository;
//import com.kitnet.kitnet.repository.UserRepository;
//import com.kitnet.kitnet.service.UserDocumentService;
//import com.kitnet.kitnet.exception.UserNotFoundException;
//import com.kitnet.kitnet.exception.ResourceNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class UserDocumentServiceImpl implements UserDocumentService {
//
//    @Autowired
//    private UserDocumentRepository userDocumentRepository;
//
//    @Autowired
//    private UserRepository userRepository; // Necessário para buscar o User
//
//    @Autowired
//    private MessageSource messageSource;
//
//    @Override
//    @Transactional
//    public UserDocument saveOrUpdateUserDocument(User user, DocumentType type, String url) {
//        if (url == null || url.isEmpty()) {
//            // Se a URL for nula ou vazia, não há documento para salvar/atualizar.
//            // Poderíamos também ter uma lógica para remover o documento se a URL for explicitamente vazia,
//            // mas por enquanto, apenas ignoramos.
//            return null;
//        }
//
//        Optional<UserDocument> existingDoc = userDocumentRepository.findByUserAndDocumentType(user, type);
//        UserDocument document;
//        if (existingDoc.isPresent()) {
//            document = existingDoc.get();
//            document.setDocumentUrl(url);
//            document.setUploadDate(LocalDate.now());
//            document.setVerificationStatus(VerificationStatus.NOT_SUBMITTED); // Resetar status para nova revisão
//            document.setRejectionReason(null);
//        } else {
//            document = new UserDocument();
//            document.setUser(user);
//            document.setDocumentType(type);
//            document.setDocumentUrl(url);
//            document.setUploadDate(LocalDate.now());
//            document.setVerificationStatus(VerificationStatus.NOT_SUBMITTED);
//        }
//        return userDocumentRepository.save(document);
//    }
//
//    @Override
//    public List<UserDocument> getUserDocuments(UUID userId) throws UserNotFoundException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.not.found", null, LocaleContextHolder.getLocale())));
//        return userDocumentRepository.findByUser(user);
//    }
//
//    @Override
//    @Transactional
//    public UserDocument updateDocumentVerificationStatus(UUID documentId, VerificationStatus status, String rejectionReason) throws ResourceNotFoundException {
//        UserDocument document = userDocumentRepository.findById(documentId)
//                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.document.not.found", null, LocaleContextHolder.getLocale())));
//
//        document.setVerificationStatus(status);
//        document.setRejectionReason(rejectionReason);
//
//        return userDocumentRepository.save(document);
//    }
//}
