package com.upliv.user_service.model.staff;

import com.upliv.user_service.constants.StaffConstants;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

public class KycDetails {

    //TODO add isActive flag - completed
    @Id
    private UUID kycDetailId;
    private StaffConstants.KYC_DOCUMENT_TYPE document_type;
    private String documentNumber;
    private String documentImageFrontUrl;
    private String documentImageBackUrl;
    private Date issuedDate;
    private Date expiryDate;
    private Date createdTimestamp;
    private Date updatedTimestamp;
    private boolean isActive;
    private StaffConstants.VERIFICATION_STATUS status;

    public KycDetails() {
        kycDetailId = UUID.randomUUID();
        createdTimestamp = new Date();
        updatedTimestamp = new Date();
    }

    public UUID getKycDetailId() {
        return kycDetailId;
    }

//    public void setKycDetailId(UUID kycDetailId) {
//        this.kycDetailId = kycDetailId;
//    }

    public StaffConstants.KYC_DOCUMENT_TYPE getDocument_type() {
        return document_type;
    }

    public void setDocument_type(StaffConstants.KYC_DOCUMENT_TYPE document_type) {
        this.document_type = document_type;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentImageFrontUrl() {
        return documentImageFrontUrl;
    }

    public void setDocumentImageFrontUrl(String documentImageFrontUrl) {
        this.documentImageFrontUrl = documentImageFrontUrl;
    }

    public String getDocumentImageBackUrl() {
        return documentImageBackUrl;
    }

    public void setDocumentImageBackUrl(String documentImageBackUrl) {
        this.documentImageBackUrl = documentImageBackUrl;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public StaffConstants.VERIFICATION_STATUS getStatus() {
        return status;
    }

    public void setStatus(StaffConstants.VERIFICATION_STATUS status) {
        this.status = status;
    }

    public KycDetails updateKycDetails(KycDetails newDetails){
//        this.document_type = newDetails.getDocument_type();
        this.documentNumber = newDetails.getDocumentNumber();
        this.documentImageFrontUrl = newDetails.getDocumentImageFrontUrl();
        this.documentImageBackUrl = newDetails.getDocumentImageBackUrl();
        this.issuedDate = newDetails.getIssuedDate();
        this.expiryDate = newDetails.getExpiryDate();
        this.updatedTimestamp = newDetails.getUpdatedTimestamp();
        this.isActive = newDetails.getActive();
        this.status = newDetails.getStatus();
        return this;
    }
}