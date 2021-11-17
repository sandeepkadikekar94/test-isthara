package com.upliv.user_service.model.staff;


import com.upliv.user_service.constants.StaffConstants;

import java.util.List;

public class WorkDetails {

    private StaffConstants.CITY_ASSIGNED cityAssigned;
    private List<String> localityAssigned;
    private StaffConstants.Department department;
    private Designation designation;
    private StaffConstants.EMPLOYMENT_TYPE employmentType;
    private List<StaffConstants.PROPERTY_NAME> propertyNames;

    public StaffConstants.CITY_ASSIGNED getCityAssigned() {
        return cityAssigned;
    }

    public void setCityAssigned(StaffConstants.CITY_ASSIGNED cityAssigned) {
        this.cityAssigned = cityAssigned;
    }

    public StaffConstants.Department getDepartment() {
        return department;
    }

    public void setDepartment(StaffConstants.Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public StaffConstants.EMPLOYMENT_TYPE getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(StaffConstants.EMPLOYMENT_TYPE employmentType) {
        this.employmentType = employmentType;
    }

    public List<StaffConstants.PROPERTY_NAME> getPropertyName() {
        return propertyNames;
    }

    public void setPropertyName(List<StaffConstants.PROPERTY_NAME> propertyName) {
        this.propertyNames = propertyName;
    }

    public List<String> getLocalityAssigned() {
        return localityAssigned;
    }

    public void setLocalityAssigned(List<String> locality) {
        this.localityAssigned = locality;
    }

    public WorkDetails updateWorkDetails(WorkDetails ref){
        this.cityAssigned = ref.getCityAssigned();
        this.department = ref.getDepartment();
        this.designation = ref.getDesignation();
        this.employmentType = ref.getEmploymentType();
        this.propertyNames = ref.getPropertyName();
        this.localityAssigned = ref.getLocalityAssigned();
        return this;
    }
}
