package com.kitnet.kitnet.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.kitnet.kitnet.dto.UserDocumentUploadDTO;
import com.kitnet.kitnet.dto.user.*;
import com.kitnet.kitnet.exception.*;
import com.kitnet.kitnet.model.*;
import com.kitnet.kitnet.model.enums.*;
import com.kitnet.kitnet.repository.*;
import com.kitnet.kitnet.service.EmailService;
import com.kitnet.kitnet.service.UserService;
import com.kitnet.kitnet.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserVerificationRepository userVerificationRepository;

    @Autowired
    private EmailService emailService;

    @Value("${app.base-url:https://seusite.com}")
    private String baseUrl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LegalDocumentRepository legalDocumentRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthResponseDTO registerSimple(UserSimpleRegisterDTO dto) throws EmailAlreadyInUseException, PasswordMismatchException {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException();
        }

        if (!dto.getAcceptTerms()) {
            throw new TermsNotAcceptedException();
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAuthorizeCreditCheckAndCommunication(false);
        user.setAcceptMarketingCommunications(false);
        user.setPhone(null);
        user.setLegalDocument(null);
        user.setLegalPersonType(null);

        Set<Role> defaultRoles = new HashSet<>();
        roleRepository.findByName(RoleName.LESSEE).ifPresent(defaultRoles::add);
        user.setRoles(defaultRoles);

        user.setAccountVerificationStatus(VerificationStatus.NOT_SUBMITTED);
        user.setMonthlyGrossIncome(null);
        user.setHasCreditRestrictions(false);
        user.setIsIdentityConfirmed(false);
        user.setProfilePictureUrl(null);
        user.setIsEmailVerified(false);
        user.setIsPhoneVerified(false);
        user.setAuthProvider(AuthProvider.EMAIL_PASSWORD);

        if (dto.getAcceptTerms()) {
            LegalDocument termsOfUse = legalDocumentRepository.findByTypeAndIsActiveTrue(LegalDocumentType.TERMS_OF_USE)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Termos de uso ativos não encontrados."));

            UserLegalDocument userLegalDoc = UserLegalDocument.builder()
                    .user(user)
                    .legalDocument(termsOfUse)
                    .type(termsOfUse.getType())
                    .acceptanceDate(LocalDate.now())
                    .build();
            user.getUserLegalDocuments().add(userLegalDoc);
        } else {
            throw new TermsNotAcceptedException();
        }

        userRepository.save(user);


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException(messageSource.getMessage("error.authentication.failed.after.registration", null, LocaleContextHolder.getLocale()));
        }

        final String jwt = jwtUtil.generateToken(user);

        UserSimpleResponseDTO userResponse = toUserSimpleResponseDTO(user);


        return new AuthResponseDTO(userResponse, jwt);
    }


    @Override
    @Transactional
    public AuthResponseDTO authenticateWithFirebase(String firebaseIdToken) throws FirebaseAuthenticationException, UserNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseIdToken);
            String firebaseUid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            boolean emailVerified = decodedToken.isEmailVerified();
            String name = decodedToken.getName();
            String photoUrl = decodedToken.getPicture();

            AuthProvider authProvider = AuthProvider.EMAIL_PASSWORD; // Default

            if (decodedToken.getClaims().containsKey("firebase") && ((Map<String, Object>) decodedToken.getClaims().get("firebase")).containsKey("sign_in_provider")) {
                String signInProvider = (String) ((Map<String, Object>) decodedToken.getClaims().get("firebase")).get("sign_in_provider");

                if (signInProvider.equals("google.com")) {
                    authProvider = AuthProvider.GOOGLE;
                } else if (signInProvider.equals("apple.com")) {
                    authProvider = AuthProvider.APPLE;
                }
            }

            Optional<User> existingUser = userRepository.findByFirebaseUid(firebaseUid);
            User user;

            if (existingUser.isPresent()) {
                user = existingUser.get();
                user.setEmail(email);
                user.setIsEmailVerified(emailVerified);
                user.setName(name);
                user.setProfilePictureUrl(photoUrl);
                user.setAuthProvider(authProvider);
                userRepository.save(user);
                System.out.println("Usuário existente logado via Firebase: " + user.getFirebaseUid());

            } else {
                user = new User();
                user.setId(UUID.randomUUID());
                user.setFirebaseUid(firebaseUid);
                user.setEmail(email);
                user.setIsEmailVerified(true);
                user.setName(name);
                user.setProfilePictureUrl(photoUrl);
                user.setAuthProvider(authProvider);

                user.setPassword(null);
                user.setAuthorizeCreditCheckAndCommunication(false);
                user.setAcceptMarketingCommunications(false);
                user.setPhone(null);
                user.setLegalDocument(null);
                user.setLegalPersonType(null);

                Set<Role> defaultRoles = new HashSet<>();
                roleRepository.findByName(RoleName.LESSEE)
                        .ifPresentOrElse(defaultRoles::add, () -> {
                            throw new RuntimeException(messageSource.getMessage("error.role.not.found.default", new Object[]{RoleName.LESSEE}, locale));
                        });
                user.setRoles(defaultRoles);

                user.setAccountVerificationStatus(VerificationStatus.NOT_SUBMITTED);
                user.setMonthlyGrossIncome(null);
                user.setHasCreditRestrictions(false);
                user.setIsIdentityConfirmed(false);

                            LegalDocument termsOfUse = legalDocumentRepository.findByTypeAndIsActiveTrue(LegalDocumentType.TERMS_OF_USE)
                        .orElseThrow(() -> new InternalServerErrorException(messageSource.getMessage("error.legal.document.not.found.active", new Object[]{LegalDocumentType.TERMS_OF_USE}, locale)));

                if (user.getUserLegalDocuments() == null) {
                    user.setUserLegalDocuments(new HashSet<>());
                }
                UserLegalDocument userLegalDoc = UserLegalDocument.builder()
                        .user(user)
                        .legalDocument(termsOfUse)
                        .type(termsOfUse.getType())
                        .acceptanceDate(LocalDate.now())
                        .build();
                user.getUserLegalDocuments().add(userLegalDoc);

                userRepository.save(user);
                System.out.println("Novo usuário criado via Firebase: " + user.getFirebaseUid());
            }

            final String jwt = jwtUtil.generateToken(user);
            UserSimpleResponseDTO userResponse = toUserSimpleResponseDTO(user);
            return new AuthResponseDTO(userResponse, jwt);

        } catch (FirebaseAuthException e) {
            throw new FirebaseAuthenticationException(messageSource.getMessage("error.firebase.auth.invalid.token", null, locale) + ": " + e.getMessage(), e);
        } catch (Exception e) {
                        throw new InternalServerErrorException(messageSource.getMessage("error.firebase.auth.internal.error", null, locale) + ": " + e.getMessage(), e);
        }
    }


    @Override
    @Transactional
    public User completeRegistrationDetails(UUID userId, UserCompleteRegistrationDTO dto)
            throws UserNotFoundException, EmailAlreadyInUseException, EmailNotVerifiedException, TermsNotAcceptedException {
        Locale locale = LocaleContextHolder.getLocale();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        messageSource.getMessage("error.user.not.found", null, locale)));

        // Verificar se o e-mail está verificado
        if (!user.getIsEmailVerified()) {
            throw new EmailNotVerifiedException(
                    messageSource.getMessage("error.email.not.verified", null, locale));
        }

        // Validar legalDocument
        if (dto.getLegalDocument() != null && !dto.getLegalDocument().isEmpty()) {
            Optional<User> existingUserWithDoc = userRepository.findByLegalDocument(dto.getLegalDocument());
            if (existingUserWithDoc.isPresent() && !existingUserWithDoc.get().getId().equals(user.getId())) {
                throw new EmailAlreadyInUseException(
                        messageSource.getMessage("error.legal.document.in.use", null, locale));
            }
            user.setLegalDocument(dto.getLegalDocument());
            user.setLegalPersonType(dto.getLegalDocument().length() == 11 ? LegalPersonType.PF : LegalPersonType.PJ);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("error.validation.field.missing", new Object[]{"legalDocument"}, locale));
        }

        // Validar phone
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            if (!dto.getPhone().equals(user.getPhone())) {
                user.setPhone(dto.getPhone());
                user.setIsPhoneVerified(false);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("error.phone.required", null, locale));
        }

        // Associar termos de LGPD e Privacidade
        if (!dto.getAcceptTermsOfLGPD()) {
            throw new TermsNotAcceptedException(
                    messageSource.getMessage("error.lgpd.terms.required", null, locale));
        }
        if (!dto.getAcceptTermsOfPrivacy()) {
            throw new TermsNotAcceptedException(
                    messageSource.getMessage("error.privacy.policy.required", null, locale));
        }

        // Buscar documentos legais ativos
        LegalDocument lgpdTerms = legalDocumentRepository.findByTypeAndIsActiveTrue(LegalDocumentType.LGPD_TERMS)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        messageSource.getMessage("error.legal.document.not.found.active",
                                new Object[]{LegalDocumentType.LGPD_TERMS}, locale)));
        LegalDocument privacyPolicy = legalDocumentRepository.findByTypeAndIsActiveTrue(LegalDocumentType.PRIVACY_POLICY)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        messageSource.getMessage("error.legal.document.not.found.active",
                                new Object[]{LegalDocumentType.PRIVACY_POLICY}, locale)));

        // Criar UserLegalDocuments
        UserLegalDocument lgpdDoc = UserLegalDocument.builder()
                .user(user)
                .legalDocument(lgpdTerms)
                .type(lgpdTerms.getType())
                .acceptanceDate(LocalDate.now())
                .build();
        UserLegalDocument privacyDoc = UserLegalDocument.builder()
                .user(user)
                .legalDocument(privacyPolicy)
                .type(privacyPolicy.getType())
                .acceptanceDate(LocalDate.now())
                .build();
        user.getUserLegalDocuments().add(lgpdDoc);
        user.getUserLegalDocuments().add(privacyDoc);

        // Validar authorizeCreditCheckAndCommunication
        if (dto.getAuthorizeCreditCheckAndCommunication() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("error.validation.field.missing",
                            new Object[]{"authorizeCreditCheckAndCommunication"}, locale));
        }
        user.setAuthorizeCreditCheckAndCommunication(dto.getAuthorizeCreditCheckAndCommunication());

        // Configurar acceptMarketingCommunications
        user.setAcceptMarketingCommunications(dto.getAcceptMarketingCommunications() != null
                ? dto.getAcceptMarketingCommunications() : false);

        // Configurar campos opcionais
        user.setProfession(dto.getProfession());
        user.setEmergencyContactName(dto.getEmergencyContactName());
        user.setEmergencyContactPhone(dto.getEmergencyContactPhone());

        // Salvar documentos
        if (dto.getDocuments() != null && !dto.getDocuments().isEmpty()) {
            // Validar documentos obrigatórios
            validateRequiredDocuments(dto.getDocuments(), user.getLegalPersonType());
            for (UserDocumentUploadDTO docDto : dto.getDocuments()) {
                saveOrUpdateUserDocument(user, docDto.getDocumentType(), docDto.getDocumentUrl());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("error.documents.required", null, locale));
        }

        // Adicionar roles adicionais
        if (dto.getAdditionalRoles() != null && !dto.getAdditionalRoles().isEmpty()) {
            for (RoleName roleName : dto.getAdditionalRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                messageSource.getMessage("error.role.not.found",
                                        new Object[]{roleName}, locale)));
                user.getRoles().add(role);
            }
        }

        // Atualizar status de verificação
        user.setAccountVerificationStatus(VerificationStatus.PENDING);
        user.setIsIdentityConfirmed(false);

        return userRepository.save(user);
    }

    private void validateRequiredDocuments(List<UserDocumentUploadDTO> documents, LegalPersonType legalPersonType) {
        List<DocumentType> requiredDocs = legalPersonType == LegalPersonType.PF
                ? List.of(DocumentType.CPF, DocumentType.SELFIE_COM_DOCUMENTO, DocumentType.COMPROVANTE_RESIDENCIA)
                : List.of(DocumentType.CNPJ, DocumentType.CONTRATO_SOCIAL, DocumentType.COMPROVANTE_RESIDENCIA);

        // Verificar se todos os documentos obrigatórios estão presentes
        for (DocumentType requiredDoc : requiredDocs) {
            boolean found = documents.stream().anyMatch(doc -> doc.getDocumentType() == requiredDoc);
            if (!found) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageSource.getMessage("error.document.required",
                                new Object[]{requiredDoc}, LocaleContextHolder.getLocale()));
            }
        }

        // Para PF, verificar se RG ou CNH foi enviado
        if (legalPersonType == LegalPersonType.PF) {
            boolean hasRg = documents.stream().anyMatch(doc ->
                    doc.getDocumentType() == DocumentType.RG_FRENTE || doc.getDocumentType() == DocumentType.RG_VERSO);
            boolean hasCnh = documents.stream().anyMatch(doc ->
                    doc.getDocumentType() == DocumentType.CNH_FRENTE || doc.getDocumentType() == DocumentType.CNH_VERSO);
            if (!hasRg && !hasCnh) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageSource.getMessage("error.identity.document.required", null, LocaleContextHolder.getLocale()));
            }
        }

        // Para REAL_ESTATE_AGENT, verificar CRECI
        if (documents.stream().anyMatch(doc -> doc.getDocumentType() == DocumentType.CRECI)) {
            boolean hasCreci = documents.stream().anyMatch(doc -> doc.getDocumentType() == DocumentType.CRECI);
            if (!hasCreci) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageSource.getMessage("error.creci.required", null, LocaleContextHolder.getLocale()));
            }
        }
    }

    private void saveOrUpdateUserDocument(User user, DocumentType type, String url) {
        Optional<UserDocument> existingDoc = userDocumentRepository.findByUserIdAndDocumentType(user.getId(), type);
        UserDocument document;
        if (existingDoc.isPresent()) {
            document = existingDoc.get();
            document.setDocumentUrl(url);
            document.setUploadDate(LocalDate.now());
            document.setVerificationStatus(VerificationStatus.NOT_SUBMITTED);
            document.setRejectionReason(null);
        } else {
            document = new UserDocument();
            document.setUser(user);
            document.setDocumentType(type);
            document.setDocumentUrl(url);
            document.setUploadDate(LocalDate.now());
            document.setVerificationStatus(VerificationStatus.NOT_SUBMITTED);
        }
        userDocumentRepository.save(document);
    }

//    @Transactional
//    public User addRoleToUser(UUID userId, RoleName roleName) throws UserNotFoundException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException());
//        Role role = roleRepository.findByName(roleName)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role " + roleName + " não encontrada."));
//
//        user.getRoles().add(role);
//        return userRepository.save(user);
//    }
//
//    // E para remover:
//    @Transactional
//    public User removeRoleFromUser(UUID userId, RoleName roleName) throws UserNotFoundException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException());
//        Role role = roleRepository.findByName(roleName)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role " + roleName + " não encontrada."));
//
//        user.getRoles().remove(role);
//        return userRepository.save(user);
//    }

    @Override
    @Transactional
    public User updateProfile(UUID userId, UserProfileUpdateDTO dto) throws UserNotFoundException, EmailAlreadyInUseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        // Validação e atualização de legalDocument e legalPersonType
        if (dto.getLegalDocument() != null && !dto.getLegalDocument().isEmpty()) {
            Optional<User> existingUserWithDoc = userRepository.findByLegalDocument(dto.getLegalDocument());
            if (existingUserWithDoc.isPresent() && !existingUserWithDoc.get().getId().equals(user.getId())) {
                throw new EmailAlreadyInUseException("Documento legal (CPF/CNPJ) já está em uso por outro usuário.");
            }
            user.setLegalDocument(dto.getLegalDocument());
            user.setLegalPersonType(dto.getLegalPersonType());
        } else {
            // Se o documento legal for nulo ou vazio no DTO, limpa na entidade se já existia
            // Isto pode ser útil se o usuário decide "remover" seus dados completos,
            // mas geralmente não é o caso para legalDocument. Considere se faz sentido.
            // Para a maioria dos casos, legalDocument seria persistente após a primeira inserção.
            // Se o DTO permite nulo e o campo no DB é nullable, não precisa fazer nada se já for nulo.
        }

        // Validação e atualização de telefone (sempre obrigatório no updateProfile)
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            // Se o telefone mudou, resetar a verificação
            if (!dto.getPhone().equals(user.getPhone())) {
                user.setPhone(dto.getPhone());
                user.setIsPhoneVerified(false); // Telefone mudou, precisa verificar novamente
            }
            // Se o telefone já era o mesmo, não faz nada com isPhoneVerified, a menos que queira revalidar periodicamente.
        } else { Locale locale = LocaleContextHolder.getLocale();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageSource.getMessage("error.phone.not.registered", null, locale));
        }


        user.setProfilePictureUrl(dto.getProfilePictureUrl());

        // Lógica para transição de status de verificação da conta:
        // Se o usuário adicionou legalDocument (ou outros dados cruciais) e estava NOT_SUBMITTED,
        // ele agora está PENDING, aguardando o envio/revisão de documentos.
        // Se já estava PENDING, REJECTED, ou APPROVED, e dados cruciais foram atualizados,
        // pode-se resetar para PENDING para uma nova revisão.
        if (user.getLegalDocument() != null &&
                (user.getAccountVerificationStatus() == VerificationStatus.NOT_SUBMITTED ||
                        user.getAccountVerificationStatus() == VerificationStatus.REJECTED ||
                        user.getAccountVerificationStatus() == VerificationStatus.APPROVED)) {
            user.setAccountVerificationStatus(VerificationStatus.PENDING); // Agora precisa de revisão manual
            user.setIsIdentityConfirmed(false); // Reseta a confirmação de identidade
        }

        user.setMonthlyGrossIncome(null); // Renda bruta é definida pelo admin/moderador
        user.setHasCreditRestrictions(false); // Restrições de crédito são definidas pelo admin/moderador

        return userRepository.save(user);
    }

//    @Override
//    @Transactional
//    public User initiatePhoneVerification(UUID userId) throws UserNotFoundException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
//        if (user.getPhone() == null || user.getPhone().isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone não cadastrado para verificação.");
//        }
//        // Em um sistema real, aqui você enviaria um SMS com SIMULATED_PHONE_CODE para user.getPhone()
//        System.out.println("DEBUG: Enviando código de telefone " + SIMULATED_PHONE_CODE + " para " + user.getPhone());
//        return user; // Não há mudança persistente aqui
//    }
//
//    @Override
//    @Transactional
//    public User completePhoneVerification(UUID userId, String code) throws UserNotFoundException, InvalidCredentialsException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
//
//        if (user.getIsPhoneVerified()) {
//            throw new InvalidCredentialsException("Telefone já verificado.");
//        }
//
//        if (SIMULATED_PHONE_CODE.equals(code)) { // Simulação: código fixo
//            user.setIsPhoneVerified(true);
//            return userRepository.save(user);
//        } else {
//            throw new InvalidCredentialsException("Código de verificação de telefone inválido.");
//        }
//    }

    // --- Métodos para Admin/Moderador (setVerificationStatus e setIncomeAndCreditStatus) ---
    @Override
    @Transactional
    public User setVerificationStatus(UUID userId, VerificationStatus status) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        user.setAccountVerificationStatus(status);
        if (status == VerificationStatus.APPROVED) {
            // Se aprovado, a identidade é confirmada (pode depender de documentos).
            user.setIsIdentityConfirmed(true);
        } else if (status == VerificationStatus.REJECTED || status == VerificationStatus.NOT_SUBMITTED) {
            user.setIsIdentityConfirmed(false);
            user.setMonthlyGrossIncome(null); // Limpa renda se rejeitado/docs não submetidos
            user.setHasCreditRestrictions(false); // Limpa restrições
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User setIncomeAndCreditStatus(UUID userId, Double income, Boolean hasCreditRestrictions) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
        user.setMonthlyGrossIncome(income);
        user.setHasCreditRestrictions(hasCreditRestrictions);
        return userRepository.save(user);
    }

    @Override
    public List<User> findUsersForVerification(VerificationStatus status) {
        return userRepository.findByAccountVerificationStatus(status);
    }

    @Override
    public AuthResponseDTO login(UserLoginDTO dto) throws UserNotFoundException, InvalidCredentialsException {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Senha incorreta.");
        }

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("Sua conta está desabilitada. Por favor, aceite os termos de uso.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Mude para sua exceção customizada aqui
            throw new InvalidCredentialsException(messageSource.getMessage("error.authentication.failed.after.login", null, LocaleContextHolder.getLocale()));
        }

        final String jwt = jwtUtil.generateToken(user);

        UserSimpleResponseDTO userResponse = toUserSimpleResponseDTO(user);

        return new AuthResponseDTO(userResponse, jwt);
    }

    @Override
    public User findById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário com ID " + id + " não encontrado."));
    }

    private UserSimpleResponseDTO toUserSimpleResponseDTO(User user) {
        UserSimpleResponseDTO dto = new UserSimpleResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setRoles(user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(java.util.stream.Collectors.toSet()));

        dto.setAccountVerificationStatus(user.getAccountVerificationStatus().toString());
        dto.setIsIdentityConfirmed(user.getIsIdentityConfirmed());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setIsPhoneVerified(user.getIsPhoneVerified());
        return dto;
    }
}