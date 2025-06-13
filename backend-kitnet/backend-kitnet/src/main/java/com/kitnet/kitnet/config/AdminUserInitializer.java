package com.kitnet.kitnet.config;

import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.Role;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.UserLegalDocument;
import com.kitnet.kitnet.model.enums.*;
import com.kitnet.kitnet.repository.LegalDocumentRepository;
import com.kitnet.kitnet.repository.RoleRepository;
import com.kitnet.kitnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@DependsOn({"legalTermsInitializer", "roleInitializer"})
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LegalDocumentRepository legalDocumentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // 2. Check and create the admin user
        if (userRepository.findByEmail("admin@kitnet.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin Kitnet");
            admin.setEmail("admin@kitnet.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhone("00000000000");
            admin.setLegalDocument("00000000000");
            admin.setLegalPersonType(LegalPersonType.PF);
            admin.setAuthorizeCreditCheckAndCommunication(true);
            admin.setAuthProvider(AuthProvider.EMAIL_PASSWORD);

            // Assign roles to admin
            Set<Role> adminRoles = new HashSet<>();
            roleRepository.findByName(RoleName.ADMIN).ifPresent(adminRoles::add);
            roleRepository.findByName(RoleName.MODERATOR).ifPresent(adminRoles::add);
            admin.setRoles(adminRoles);

            admin.setAccountVerificationStatus(VerificationStatus.APPROVED);
            admin.setIsIdentityConfirmed(true);
            admin.setMonthlyGrossIncome(99999.99);
            admin.setHasCreditRestrictions(false);
            admin.setIsEmailVerified(true);
            admin.setIsPhoneVerified(true);
            admin.setProfilePictureUrl("https://i.imgur.com/your-admin-profile-pic.png");

            // Associated TERMS_OF_USE document
            LegalDocument termsOfUse = legalDocumentRepository.findByTypeAndIsActiveTrue(LegalDocumentType.TERMS_OF_USE)
                    .orElseGet(() -> {
                        LegalDocument tempDoc = new LegalDocument();
                        tempDoc.setType(LegalDocumentType.TERMS_OF_USE);
                        tempDoc.setVersion("1.0");
                        tempDoc.setContent("Temporary Terms of Use for Admin");
                        tempDoc.setEffectiveDate(LocalDate.now());
                        tempDoc.setIsActive(true);
                        return legalDocumentRepository.save(tempDoc);
                    });
            UserLegalDocument userLegalDoc = UserLegalDocument.builder()
                    .user(admin)
                    .legalDocument(termsOfUse)
                    .type(termsOfUse.getType()) // Defines the type of legal document
                    .acceptanceDate(LocalDate.now())
                    .build();
            admin.getUserLegalDocuments().add(userLegalDoc);

            userRepository.save(admin);
            System.out.println("Usuário ADMIN padrão criado: admin@kitnet.com / admin123");
        }
    }
}