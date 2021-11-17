package com.upliv.user_service.model.staff;


import com.upliv.user_service.constants.StaffConstants;

import java.util.Date;

public class StaffProfessionalDetails {

    private WorkDetails workDetails;
    private SalaryDetails salaryDetails;
    private ResignationDetails resignationDetails;
    private Date dateOfJoining;
    private Date latestLoginDate;
    private StaffConstants.VERIFICATION_STATUS kycStatus;

    public StaffProfessionalDetails(){
        this.dateOfJoining = new Date();
        this.latestLoginDate = new Date();
//        loginCredentials.generateDefaultCredentials(5, 10);
    }

    public WorkDetails getWorkDetails() {
        return workDetails;
    }

    public void setWorkDetails(WorkDetails workDetails) {
        this.workDetails = workDetails;
    }

    public SalaryDetails getSalaryDetails() {
        return salaryDetails;
    }

    public void setSalaryDetails(SalaryDetails salaryDetails) {
        this.salaryDetails = salaryDetails;
    }

    public ResignationDetails getResignationDetails() {
        return resignationDetails;
    }

    public void setResignationDetails(ResignationDetails resignationDetails) {
        this.resignationDetails = resignationDetails;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Date getLatestLoginDate() {
        return latestLoginDate;
    }

    public void setLatestLoginDate(Date latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
    }

    public StaffConstants.VERIFICATION_STATUS getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(StaffConstants.VERIFICATION_STATUS kycStatus) {
        this.kycStatus = kycStatus;
    }

    public StaffProfessionalDetails updateProfessionalDetails(StaffProfessionalDetails ref){
        this.workDetails = ref.getWorkDetails();
        this.salaryDetails = ref.getSalaryDetails();
        this.resignationDetails = ref.getResignationDetails();
        this.dateOfJoining = ref.getDateOfJoining();
        this.latestLoginDate = ref.getLatestLoginDate();
        this.kycStatus = ref.getKycStatus();
        return this;
    }
}
