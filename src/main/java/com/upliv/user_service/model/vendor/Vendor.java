package com.upliv.user_service.model.vendor;

import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.model.AddressDetails;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
public class Vendor {
    @Id
    private UUID vendorId;
    @NotNull
    private String companyName;
    @NotNull
    private String countryCode;
    @NotNull
    private String mobile;
    private String email;
    private String profilePicture;
    private boolean isActive;
    private boolean isVerified;
    @NotNull
    private List<UserConstants.VendorServiceType> services;
    private AddressDetails addressDetails;
    private String companyWebPage;
    private String gstNumber;
    private String cin;
    //TODO add payment details

    private Date createdTimeStamp;
    private Date updatedTimeStamp;

    public Vendor() {
        vendorId = UuidUtil.getTimeBasedUuid();
        createdTimeStamp = new Date();
        updatedTimeStamp = new Date();
        isActive = true;
        isVerified = false;
    }


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Date getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(Date updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<UserConstants.VendorServiceType> getServices() {
        return services;
    }

    public void setServices(List<UserConstants.VendorServiceType> services) {
        this.services = services;
    }

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getCompanyWebPage() {
        return companyWebPage;
    }

    public void setCompanyWebPage(String companyWebPage) {
        this.companyWebPage = companyWebPage;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
}
