package com.kitnet.kitnet.config;

import com.kitnet.kitnet.model.Role;
import com.kitnet.kitnet.model.RoleName;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.LegalPersonType;
import com.kitnet.kitnet.model.VerificationStatus;
import com.kitnet.kitnet.repository.RoleRepository; // Importe RoleRepository
import com.kitnet.kitnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // Injete RoleRepository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Criar e persistir todas as roles se não existirem
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role newRole = new Role(null, roleName);
                        return roleRepository.save(newRole);
                    });
        }
        System.out.println("Roles padrão garantidas no banco de dados.");

        // 2. Verificar e criar o usuário admin
        if (userRepository.findByEmail("admin@kitnet.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin Kitnet");
            admin.setEmail("admin@kitnet.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhone("00000000000");
            admin.setAcceptTerms(true);
            admin.setLegalDocument("00000000000");
            admin.setLegalPersonType(LegalPersonType.PF);

            // Atribuir as roles ao admin
            Set<Role> adminRoles = new HashSet<>();
            roleRepository.findByName(RoleName.ADMIN).ifPresent(adminRoles::add);
            roleRepository.findByName(RoleName.MODERATOR).ifPresent(adminRoles::add); // Admin também é moderador
            admin.setRoles(adminRoles); // <--- Atribuindo as roles

            admin.setAccountVerificationStatus(VerificationStatus.APPROVED);
            admin.setIsIdentityConfirmed(true);
            admin.setMonthlyGrossIncome(99999.99);
            admin.setHasCreditRestrictions(false);
            admin.setIsEmailVerified(true);
            admin.setIsPhoneVerified(true);
            admin.setProfilePictureUrl("https://i.imgur.com/your-admin-profile-pic.png");

            userRepository.save(admin);
            System.out.println("Usuário ADMIN padrão criado: admin@kitnet.com / admin123");
        }
    }
}