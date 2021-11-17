package com.upliv.user_service.model.staff;

import com.upliv.user_service.constants.StaffConstants;
import com.upliv.user_service.model.common.ContactDetails;
import com.upliv.user_service.rbac.models.ResourcePermissions;
import com.upliv.user_service.rbac.models.RoleConstants;

import java.util.List;

public class AdminStaff {

    private ContactDetails details;
    private StaffConstants.ADMIN_TYPE type;
//    private UserPermissions permissions;
//    private boolean allModules;
    private List<RoleConstants.Modules> modules;
//    private boolean allResources;
    private List<ResourcePermissions> permissions;

    public AdminStaff(){
//        System.out.println(type);
//        this.details.setName(type.name());
    }

    public ContactDetails getDetails() {
        return details;
    }

    public void setDetails(ContactDetails details) {
        this.details = details;
    }

    public StaffConstants.ADMIN_TYPE getType() {
        return type;
    }

    public void setType(StaffConstants.ADMIN_TYPE type) {
        this.type = type;
    }

    public List<RoleConstants.Modules> getModules() {
        return modules;
    }

    public void setModules(List<RoleConstants.Modules> modules) {
        this.modules = modules;
    }

    public List<ResourcePermissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ResourcePermissions> permissions) {
        this.permissions = permissions;
    }

    public AdminStaff updateAdminStaff(AdminStaff request){

        this.details = request.getDetails();
        this.type = request.getType();
        this.permissions = request.getPermissions();
        return this;
    }

    @Override
    public String toString() {
        return "AdminStaff{" +
                "details=" + details +
                ", type=" + type +
                ", permissions=" + permissions +
                '}';
    }
}
