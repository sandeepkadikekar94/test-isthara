package com.upliv.user_service.rbac.service;

import com.upliv.user_service.exceptions.ErrorCodes;
import com.upliv.user_service.exceptions.ErrorMessage;
import com.upliv.user_service.exceptions.ResourceNotFoundException;
import com.upliv.user_service.model.vendor.Vendor;
import com.upliv.user_service.rbac.models.Module;
import com.upliv.user_service.rbac.models.Role;
import com.upliv.user_service.rbac.models.UserPermissions;
import com.upliv.user_service.rbac.repository.ModulesRepository;
import com.upliv.user_service.rbac.repository.RoleRepository;
import com.upliv.user_service.rbac.repository.UserPermissionsRepository;
import com.upliv.user_service.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RbacService {

    @Autowired
    private ModulesRepository modulesRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserPermissionsRepository permissionsRepository;

    public Role createRole(Role module) {
        return roleRepository.save(module);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    public UserPermissions updateUserPermissions(UserPermissions userPermission) {
        return permissionsRepository.save(userPermission);
    }

    public UserPermissions getUserPermissions(UUID userId) {
        Optional<UserPermissions> optionalPermissions = permissionsRepository.findById(userId);
        return optionalPermissions.get();
    }

    public String assignrole(UUID roleId, UUID userId) {
         Optional<Role> role = roleRepository.findById(roleId);
         UserPermissions permissions = new UserPermissions();
         role.ifPresentOrElse(access -> {
             permissions.setUserId(userId);
             if(access.getPermissions().isAdminAccess()) {
                 permissions.setAdminAccess(true);
                 permissionsRepository.save(permissions);
             }
             else if(access.getPermissions() != null) {
                 permissions.setPermissions(access.getPermissions().getPermissions());
                 permissionsRepository.save(permissions);
             }

         },  () -> {
             throw new ResourceNotFoundException(String.format(ErrorMessage.RECORD_NOT_FOUND, "Role"), ErrorCodes.RECORD_NOT_FOUND);
         });
        return "Access Given";
    }

//    public Permission createPermission(Permission permission) {
//        return permissionsRepository.save(permission);
//    }
//
//    public List<Permission> getAllPermissions() {
//        return permissionsRepository.findAll();
//    }
}
