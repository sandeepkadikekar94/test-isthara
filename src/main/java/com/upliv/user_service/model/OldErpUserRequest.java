package com.upliv.user_service.model;

import com.upliv.user_service.constants.UserConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class OldErpUserRequest {



    @NotNull
    private String name;
    private String email;
    @NotNull
    private String mobile;
    private Date checkinDate;
    private UserConstants.REQUEST_TYPE requestType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public UserConstants.REQUEST_TYPE getRequestType() {
        return requestType;
    }

    public void setRequestType(UserConstants.REQUEST_TYPE requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "OldErpUserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", checkinDate=" + checkinDate +
                ", requestType=" + requestType +
                '}';
    }
}
