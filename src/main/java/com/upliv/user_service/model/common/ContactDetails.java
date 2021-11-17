package com.upliv.user_service.model.common;

import javax.validation.constraints.NotNull;

public class ContactDetails {
    @NotNull
    private String name;
    private Mobile mobile;
    private String workEmailId;
    private String personalEmailId;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public String getWorkEmailId() {
        return workEmailId;
    }

    public void setWorkEmailId(String workEmailId) {
        this.workEmailId = workEmailId;
    }

    public String getPersonalEmailId() {
        return personalEmailId;
    }

    public void setPersonalEmailId(String personalEmailId) {
        this.personalEmailId = personalEmailId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ContactDetails updateContactDetails(ContactDetails details){
        this.name = details.getName();
        this.mobile = details.getMobile();
        this.workEmailId = details.getWorkEmailId();
        this.personalEmailId = details.getPersonalEmailId();
        this.imageUrl = details.getImageUrl();
        return this;
    }
}
