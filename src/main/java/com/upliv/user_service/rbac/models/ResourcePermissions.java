package com.upliv.user_service.rbac.models;

import java.util.Set;
import java.util.UUID;

public class ResourcePermissions {

    private RoleConstants.Permission permission;
    private Set<UUID> resourceIds;
    private boolean allResources;

    public RoleConstants.Permission getPermission() {
        return permission;
    }

    public void setPermission(RoleConstants.Permission permission) {
        this.permission = permission;
    }

    public Set<UUID> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<UUID> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public boolean isAllResources() {
        return allResources;
    }

    public void setAllResources(boolean allResources) {
        this.allResources = allResources;
    }

    @Override
    public String toString() {
        return "ResourcePermissions{" +
                "permission=" + permission +
                ", resourceIds=" + resourceIds +
                ", allResources=" + allResources +
                '}';
    }
}
