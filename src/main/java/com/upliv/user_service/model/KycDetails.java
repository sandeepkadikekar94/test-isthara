package com.upliv.user_service.model;

import java.util.Date;

public class KycDetails {

    public enum  KYC_DOCUMENT_TYPE {
        AADHAR, PAN, VOTER_ID, DRIVING_LICENCE, PASSPORT
    }
    private KYC_DOCUMENT_TYPE document_type;
    private String documentNumber;
    private String documentImageFrontUrl;
    private String documentImageBackUrl;
    private Date issuedDate;
    private Date expiryDate;
    private Date createdTimestamp;
    private Date updatedTimestamp;

    public KYC_DOCUMENT_TYPE getDocument_type() {
        return document_type;
    }

    public void setDocument_type(KYC_DOCUMENT_TYPE document_type) {
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
}
