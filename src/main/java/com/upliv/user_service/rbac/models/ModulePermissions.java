package com.upliv.user_service.rbac.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ModulePermissions {

    private RoleConstants.Modules module;
    private List<ResourcePermissions> permissions;
    private boolean moduleAdmin;

    public RoleConstants.Modules getModule() {
        return module;
    }

    public void setModule(RoleConstants.Modules module) {
        this.module = module;
    }

    public List<ResourcePermissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ResourcePermissions> permissions) {
        this.permissions = permissions;
    }

    public boolean isModuleAdmin() {
        return moduleAdmin;
    }

    public void setModuleAdmin(boolean moduleAdmin) {
        this.moduleAdmin = moduleAdmin;
    }

    @Override
    public String toString() {
        return "ModulePermissions{" +
                "module=" + module +
                ", permissions=" + permissions +
                ", moduleAdmin=" + moduleAdmin +
                '}';
    }
}
