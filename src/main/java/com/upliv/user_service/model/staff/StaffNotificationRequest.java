package com.upliv.user_service.model.staff;

import com.upliv.user_service.model.common.SmsRequest;

public class StaffNotificationRequest {

    private SmsRequest smsRequest;
    private String subject;
    private String body;
    private String toEmailId;
    private String ccEmail;
    private String bccEmail;

    public SmsRequest getSmsRequest() {
        return smsRequest;
    }

    public void setSmsRequest(SmsRequest smsRequest) {
        this.smsRequest = smsRequest;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToEmailId() {
        return toEmailId;
    }

    public void setToEmailId(String toEmailId) {
        this.toEmailId = toEmailId;
    }

    public String getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getBccEmail() {
        return bccEmail;
    }

    public void setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
    }
}
