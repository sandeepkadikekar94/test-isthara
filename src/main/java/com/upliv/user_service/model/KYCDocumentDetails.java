package com.upliv.user_service.model;

public class KYCDocumentDetails {
    public enum  KYC_TYPE {
        IDENTITY_PROOF, ADDRESS_PROOF, PASSPORT_SIZE_PHOTO
    }
    private KYC_TYPE type;
    private String documentURL;
    private String documentName;


    public KYCDocumentDetails() {
    }

    public String getDocumentURL()
    {
        return  documentURL;
    }
    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
    }

    public KYC_TYPE getType() {
        return type;
    }

    public void setType(KYC_TYPE type) {
        this.type = type;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
