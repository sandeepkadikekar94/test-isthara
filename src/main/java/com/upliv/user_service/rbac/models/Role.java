package com.upliv.user_service.rbac.models;

import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Document
public class Role {
    @Id
    private UUID roleId;
    private String roleName;
    private UserPermissions permissions;
//    private Map<RoleConstants.Modules, List<RoleConstants.Permission>> modulePermissions;

    public Role() {
        roleId = UuidUtil.getTimeBasedUuid();
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public UserPermissions getPermissions() {
        return permissions;
    }

    public void setPermissions(UserPermissions permissions) {
        this.permissions = permissions;
    }

    //    public Map<RoleConstants.Modules, List<RoleConstants.Permission>> getModulePermissions() {
//        return modulePermissions;
//    }
//
//    public void setModulePermissions(Map<RoleConstants.Modules, List<RoleConstants.Permission>> modulePermissions) {
//        this.modulePermissions = modulePermissions;
//    }
}
