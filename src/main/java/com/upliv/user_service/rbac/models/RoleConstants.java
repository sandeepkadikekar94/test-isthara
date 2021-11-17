package com.upliv.user_service.rbac.models;

public class RoleConstants {
    public enum Permission {
        //E-Execute, L-List, NA - Not_allowed, D- details
        //P-Property, S-Staff


        /*****    Property permissions (P-Property)    *****/
        P_CREATE( "Create Property"),
        P_EDIT_BASIC( "Edit Property basic details"),
        P_EDIT_IMAGE( "Edit Property images"),
        P_EDIT( "Edit Property"),
        P_VIEW("View All Property List"),
        P_DELETE("Delete Property"),



        /*****    Staff permissions (S-Staff)    *****/
        S_CREATE( "Create Staff"),
        S_EDIT_BASIC("Edit staff basic details"),
        S_EDIT_ALL("Edit staff all details"),
        S_VIEW("View All Staff List"),
        S_DELETE("Delete Staff"),



        /*****    User Permissions (U-User)    *****/
        U_CREATE( "Create User"),
        U_EDIT_BASIC("Edit user basic details"),
        U_EDIT_ALL("Edit user all details"),
        U_VIEW("View Users"),


        /**** Enquiry Permissions(E-Enquiry)  *****/
        E_CREATE("Create Enquiry"),
        E_VIEW("View Enquiries"),
        E_EDIT_BASIC("Edit Enquiries basic details"),
        E_EDIT_ALL("Edit  Enquiries All Details"),
        E_DELETE("DELETE Enquiries")

        ;

        private final String desc;

        Permission(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum Modules {
        //E-Execute, L-List, NA - Not_allowed, D- details
        //P-Property, S-Staff, U-User

        PROPERTY,
        STAFF,
        USER,
        ENQUIRY,
        ;
    }
}
