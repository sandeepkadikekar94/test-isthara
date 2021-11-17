package com.upliv.user_service.model.staff;


import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.model.AddressDetails;
import com.upliv.user_service.model.common.ContactDetails;

import java.util.Date;

public class PersonalDetails {

    private AddressDetails addressDetails;
    private UserConstants.Gender gender;
    private UserConstants.MARITAL_STATUS maritalStatus;
    private Date dateOfBirth;

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    public UserConstants.Gender getGender() {
        return gender;
    }

    public void setGender(UserConstants.Gender gender) {
        this.gender = gender;
    }

    public UserConstants.MARITAL_STATUS getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(UserConstants.MARITAL_STATUS maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PersonalDetails updatePersonalDetails(PersonalDetails ref){
        this.addressDetails = ref.getAddressDetails();
        this.dateOfBirth = ref.getDateOfBirth();
        this.gender = ref.getGender();
        this.maritalStatus = ref.getMaritalStatus();
        return this;
    }
}