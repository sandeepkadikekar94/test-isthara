package com.upliv.user_service.rbac.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
public class UserPermissions {
    @Id
    private UUID userId;
    private List<ModulePermissions> permissions;
    private boolean adminAccess;

    public boolean isAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(boolean adminAccess) {
        this.adminAccess = adminAccess;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<ModulePermissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ModulePermissions> permissions) {
        this.permissions = permissions;
    }
}
