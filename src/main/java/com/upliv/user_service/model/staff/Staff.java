package com.upliv.user_service.model.staff;

import com.upliv.user_service.model.common.ContactDetails;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Document
public class Staff {

    @Id
    private UUID staffId;
    private String displayId;
    @NotNull
    private ContactDetails contactDetails;
    @DBRef(lazy = true)
    private StaffDetails staffDetails;

    //TODO: move to different collection
    private LoginCredentials loginCredentials;

    private Date createTimeStamp;
    private Date updatedTimeStamp;
    private boolean resetPassword;
    private boolean isActive;
//    private List<IssueDetails> issueDetails;

    public Staff() {
        this.staffId = UuidUtil.getTimeBasedUuid();
        this.createTimeStamp = new Date();
        this.updatedTimeStamp = createTimeStamp;
    }

    public StaffDetails getStaffDetails() {
        return staffDetails;
    }

    public void setStaffDetails(StaffDetails staffDetails) {
        this.staffDetails = staffDetails;
    }

    public UUID getStaffId() {
        return staffId;
    }

    public void setStaffId(UUID staffId) {
        this.staffId = staffId;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Date getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(Date createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public Date getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(Date updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public boolean isResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LoginCredentials getLoginCredentials() {
        return loginCredentials;
    }

    public void setLoginCredentials(LoginCredentials loginCredentials) {
        this.loginCredentials = loginCredentials;
    }

    public Staff updateStaffDetails(Staff ref){
        this.loginCredentials = ref.getLoginCredentials();
        this.contactDetails = ref.getContactDetails();
        this.resetPassword = ref.isResetPassword();
        this.isActive = ref.isActive();
        this.updatedTimeStamp = new Date();

        return this;
    }
}
