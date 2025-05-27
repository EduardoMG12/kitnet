package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.*;
import com.kitnet.kitnet.dto.user.*;
import com.kitnet.kitnet.model.Role;
import com.kitnet.kitnet.model.RoleName;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.VerificationStatus;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.UserService;
import com.kitnet.kitnet.exception.EmailAlreadyInUseException;
import com.kitnet.kitnet.exception.InvalidCredentialsException;
import com.kitnet.kitnet.exception.PasswordMismatchException;
import com.kitnet.kitnet.exception.UserNotFoundException;
import com.kitnet.kitnet.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.kitnet.kitnet.repository.RoleRepository;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Estes campos não serão persistidos na entidade User, mas simulados em memória ou em uma tabela temporária.
    // Para um projeto real, você usaria um serviço de cache (Redis) ou uma tabela separada para códigos de verificação.
    // Para simplificar no projeto de faculdade, podemos simular.
    private static final String SIMULATED_EMAIL_CODE = "123456";
    private static final String SIMULATED_PHONE_CODE = "654321";
    // Pode ser interessante mapear UUID do usuário para o código e timestamp para expiração
    // private Map<UUID, String> emailVerificationCodes = new ConcurrentHashMap<>();
    // private Map<UUID, LocalDateTime> emailCodeExpiry = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public AuthResponseDTO registerSimple(UserSimpleRegisterDTO dto) throws EmailAlreadyInUseException, PasswordMismatchException {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException();
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAcceptTerms(dto.getAcceptTerms());
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

    // Dentro de UserServiceImpl, adicione um método para adicionar roles:
    @Transactional
    public User addRoleToUser(UUID userId, RoleName roleName) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role " + roleName + " não encontrada."));

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    // E para remover:
    @Transactional
    public User removeRoleFromUser(UUID userId, RoleName roleName) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role " + roleName + " não encontrada."));

        user.getRoles().remove(role);
        return userRepository.save(user);
    }

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

    // --- Métodos de Verificação de Email e Telefone (Simulados) ---

    @Override
    @Transactional
    public User initiateEmailVerification(UUID userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
        // Em um sistema real, aqui você enviaria um e-mail com SIMULATED_EMAIL_CODE para user.getEmail()
        System.out.println("DEBUG: Enviando código de e-mail " + SIMULATED_EMAIL_CODE + " para " + user.getEmail());
        // Para um projeto de faculdade, você não precisa armazenar o código no User,
        // pois a validação é simplificada.
        return user; // Não há mudança persistente aqui, apenas "inicia" o processo
    }

    @Override
    @Transactional
    public User completeEmailVerification(UUID userId, String code) throws UserNotFoundException, InvalidCredentialsException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        if (user.getIsEmailVerified()) {
            throw new InvalidCredentialsException("E-mail já verificado.");
        }

        if (SIMULATED_EMAIL_CODE.equals(code)) { // Simulação: código fixo
            user.setIsEmailVerified(true);
            return userRepository.save(user);
        } else {
            throw new InvalidCredentialsException("Código de verificação de e-mail inválido.");
        }
    }

    @Override
    @Transactional
    public User initiatePhoneVerification(UUID userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone não cadastrado para verificação.");
        }
        // Em um sistema real, aqui você enviaria um SMS com SIMULATED_PHONE_CODE para user.getPhone()
        System.out.println("DEBUG: Enviando código de telefone " + SIMULATED_PHONE_CODE + " para " + user.getPhone());
        return user; // Não há mudança persistente aqui
    }

    @Override
    @Transactional
    public User completePhoneVerification(UUID userId, String code) throws UserNotFoundException, InvalidCredentialsException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        if (user.getIsPhoneVerified()) {
            throw new InvalidCredentialsException("Telefone já verificado.");
        }

        if (SIMULATED_PHONE_CODE.equals(code)) { // Simulação: código fixo
            user.setIsPhoneVerified(true);
            return userRepository.save(user);
        } else {
            throw new InvalidCredentialsException("Código de verificação de telefone inválido.");
        }
    }

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