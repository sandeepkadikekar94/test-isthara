package com.upliv.user_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
public class UserDetails {
    @Id
    private UUID userId;
    private BankDetails bankDetails;
    private EmergencyDetails emergencyDetails;
    private ProfessionalDetails professionalDetails;
    private List<KYCDocumentDetails> kycDocumentDetails;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public EmergencyDetails getEmergencyDetails() {
        return emergencyDetails;
    }

    public void setEmergencyDetails(EmergencyDetails emergencyDetails) {
        this.emergencyDetails = emergencyDetails;
    }

    public ProfessionalDetails getProfessionalDetails() {
        return professionalDetails;
    }

    public void setProfessionalDetails(ProfessionalDetails professionalDetails) {
        this.professionalDetails = professionalDetails;
    }

    public List<KYCDocumentDetails> getKycDocumentDetails() {
        return kycDocumentDetails;
    }

    public void setKycDocumentDetails(List<KYCDocumentDetails> kycDocumentDetails) {
        this.kycDocumentDetails = kycDocumentDetails;
    }

    public UserDetails()
    {
        this.createdTimeStamp = new Date();
        this.updatedTimeStamp = new Date();
    }
    public void updateUserDetails(UserDetails newUserDetails) {
        this.emergencyDetails = newUserDetails.emergencyDetails !=null ? newUserDetails.emergencyDetails : this.emergencyDetails;
        this.bankDetails = newUserDetails.bankDetails !=null ? newUserDetails.bankDetails : this.bankDetails;
        this.professionalDetails = newUserDetails.professionalDetails !=null ? newUserDetails.professionalDetails : this.professionalDetails;
        this.kycDocumentDetails = newUserDetails.kycDocumentDetails !=null ? newUserDetails.kycDocumentDetails : this.kycDocumentDetails;
        this.updatedTimeStamp = new Date();
    }
}
