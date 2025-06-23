package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.Role;
import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.repository.RoleRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.RoleService;
import com.kitnet.kitnet.exception.RoleNotFoundException;
import com.kitnet.kitnet.exception.UnauthorizedRoleAssignmentException;
import com.kitnet.kitnet.exception.InvalidRoleOperationException;
import com.kitnet.kitnet.exception.UserNotFoundException; // Se usar findById aqui
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public User assignRoleToUser(UUID userId, RoleName roleToAssign, UUID actingUserId) throws RoleNotFoundException, UnauthorizedRoleAssignmentException, UserNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.not.found", null, locale)));

        Role role = roleRepository.findByName(roleToAssign)
                .orElseThrow(() -> new RoleNotFoundException(messageSource.getMessage("error.role.not.found", new Object[]{roleToAssign}, locale)));

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        if (roleToAssign == RoleName.ADMIN && !actingUser.hasRole(RoleName.ADMIN)) {
            throw new UnauthorizedRoleAssignmentException(messageSource.getMessage("error.role.admin.only.admin", null, locale));
        }


        if (!actingUser.hasRole(RoleName.ADMIN) &&
                !Set.of(RoleName.LESSOR, RoleName.LESSEE, RoleName.REAL_ESTATE_AGENT).contains(roleToAssign)) {
            throw new UnauthorizedRoleAssignmentException(messageSource.getMessage("error.role.assign.unauthorized", null, locale));
        }

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User removeRoleFromUser(UUID userId, RoleName roleToRemove, UUID actingUserId) throws RoleNotFoundException, UnauthorizedRoleAssignmentException, InvalidRoleOperationException, UserNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.not.found", null, locale)));

        Role role = roleRepository.findByName(roleToRemove)
                .orElseThrow(() -> new RoleNotFoundException(messageSource.getMessage("error.role.not.found", new Object[]{roleToRemove}, locale)));

        // Regra de negócio: Não pode remover a role padrão LESSEE
        if (roleToRemove == RoleName.LESSEE) {
            throw new InvalidRoleOperationException(messageSource.getMessage("error.role.lessee.cannot.remove", null, locale));
        }

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));


        if (roleToRemove == RoleName.ADMIN && !actingUser.hasRole(RoleName.ADMIN)) {
            throw new UnauthorizedRoleAssignmentException(messageSource.getMessage("error.role.admin.only.admin.remove", null, locale));
        }
        if (!actingUser.hasRole(RoleName.ADMIN) &&
                !Set.of(RoleName.LESSOR, RoleName.REAL_ESTATE_AGENT).contains(roleToRemove)) {
            throw new UnauthorizedRoleAssignmentException(messageSource.getMessage("error.role.remove.unauthorized", null, locale));
        }

        user.getRoles().remove(role);
        return userRepository.save(user);
    }

    @Override
    public boolean roleExists(RoleName roleName) {
        return roleRepository.findByName(roleName).isPresent();
    }

    @Override
    public Role getRoleByName(RoleName roleName) throws RoleNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(messageSource.getMessage("error.role.not.found", new Object[]{roleName}, locale)));
    }
}