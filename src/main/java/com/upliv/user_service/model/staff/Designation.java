package com.upliv.user_service.model.staff;


import com.upliv.user_service.constants.StaffConstants;

public class Designation {

    private StaffConstants.OPERATIONS_DESIGNATION operationsDesignation;
    private StaffConstants.CORPORATE_DESIGNATION corporateDesignation;
//    private StaffConstants.Department department;

    public StaffConstants.OPERATIONS_DESIGNATION getOperationsDesignation() {
        return operationsDesignation;
    }

    public void setOperationsDesignation(StaffConstants.OPERATIONS_DESIGNATION operationsDesignation) {
        this.operationsDesignation = operationsDesignation;
    }

    public StaffConstants.CORPORATE_DESIGNATION getCorporateDesignation() {
        return corporateDesignation;
    }

    public void setCorporateDesignation(StaffConstants.CORPORATE_DESIGNATION corporateDesignation) {
        this.corporateDesignation = corporateDesignation;
    }
//
//    public StaffConstants.Department getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(StaffConstants.Department department) {
//        this.department = department;
//    }
}
