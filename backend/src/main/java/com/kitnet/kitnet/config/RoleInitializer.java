package com.kitnet.kitnet.config;

import com.kitnet.kitnet.model.Role;
import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@DependsOn("legalTermsInitializer")
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName)
                    .ifPresentOrElse(
                            r -> {},
                            () -> {
                                Role newRole = new Role();
                                newRole.setName(roleName);
                                roleRepository.save(newRole);
                                System.out.println("Role " + roleName.name() + " guaranteed in the database.");
                            }
                    );
        }
        System.out.println("Default roles guaranteed in the database.");
    }
}