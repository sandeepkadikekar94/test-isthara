package com.upliv.user_service.model.common;


public class EmergencyContact {

    private String relationshipWithEmployee;
    private ContactDetails contactDetails;

    public String getRelationshipWithEmployee() {
        return relationshipWithEmployee;
    }

    public void setRelationshipWithEmployee(String relationshipWithEmployee) {
        this.relationshipWithEmployee = relationshipWithEmployee;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public EmergencyContact updateEmergencyContact(EmergencyContact request){
        this.relationshipWithEmployee = request.getRelationshipWithEmployee();
        this.contactDetails = request.getContactDetails();

        return this;
    }
}
