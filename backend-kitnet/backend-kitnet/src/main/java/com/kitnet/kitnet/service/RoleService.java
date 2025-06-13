// src/main/java/com/kitnet.kitnet.service/RoleService.java
package com.kitnet.kitnet.service;

import com.kitnet.kitnet.exception.UserNotFoundException;
import com.kitnet.kitnet.model.Role;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.exception.RoleNotFoundException; // Crie esta exceção
import com.kitnet.kitnet.exception.UnauthorizedRoleAssignmentException; // Crie esta exceção
import com.kitnet.kitnet.exception.InvalidRoleOperationException; // Crie esta exceção

import java.util.UUID;

public interface RoleService {
    // For Admin Operation.
    User assignRoleToUser(UUID userId, RoleName roleToAssign, UUID actingUserId) throws RoleNotFoundException, UnauthorizedRoleAssignmentException, UserNotFoundException;
    User removeRoleFromUser(UUID userId, RoleName roleToRemove, UUID actingUserId) throws RoleNotFoundException, UnauthorizedRoleAssignmentException, InvalidRoleOperationException, UserNotFoundException;

    Role getRoleByName(RoleName roleName) throws RoleNotFoundException; // Novo método

    // utils methods
    boolean roleExists(RoleName roleName);
}