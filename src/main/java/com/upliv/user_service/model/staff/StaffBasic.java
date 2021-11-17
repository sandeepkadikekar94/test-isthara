package com.upliv.user_service.model.staff;

import com.upliv.user_service.model.AddressDetails;
import com.upliv.user_service.model.common.ContactDetails;
import com.upliv.user_service.model.common.EmergencyContact;
import com.upliv.user_service.model.common.Mobile;

import java.util.List;

public class StaffBasic {

    private ContactDetails contactDetails;
    private AddressDetails addressDetails;
    private EmergencyContact emergencyContact;
    private List<KycDetails> kycDetails;

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public List<KycDetails> getKycDetails() {
        return kycDetails;
    }

    public void setKycDetails(List<KycDetails> kycDetails) {
        this.kycDetails = kycDetails;
    }

    //    public KycDetails getKycDetails() {
//        return kycDetails;
//    }
//
//    public void setKycDetails(KycDetails kycDetails) {
//        this.kycDetails = kycDetails;
//    }
}
