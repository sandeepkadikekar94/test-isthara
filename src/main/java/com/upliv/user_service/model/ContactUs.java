package com.upliv.user_service.model;

import com.upliv.user_service.model.common.Mobile;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;
@Document
public class ContactUs {
    @Id
    private UUID contactUsId;
    @NotNull @Size(min = 3,max = 50)
    private String name;
    @NotNull @Email
    private String email;
    @NotNull
    private Mobile mobile;
    @NotNull
    private String message;
    private Date createdDateTimeStamp;

    public ContactUs()
    {
        contactUsId = UuidUtil.getTimeBasedUuid();
        createdDateTimeStamp = new Date();
    }

    public UUID getContactUsId() {
        return contactUsId;
    }

    public void setContactUsId(UUID contactUsId) {
        this.contactUsId = contactUsId;
    }

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

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDateTimeStamp() {
        return createdDateTimeStamp;
    }

    public void setCreatedDateTimeStamp(Date createdDateTimeStamp) {
        this.createdDateTimeStamp = createdDateTimeStamp;
    }

    @Override
    public String toString() {
        return "ContactUs{" +
                "contactUsId=" + contactUsId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", createdDateTimeStamp=" + createdDateTimeStamp +
                '}';
    }
}
