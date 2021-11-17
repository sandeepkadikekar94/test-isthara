package com.upliv.user_service.model.common;

import java.util.List;

public class EmailRequest {

    private String subject;
    private String body;
    private List<String> toEmail;
    private List<String> ccEmail;
    private List<String> bccEmail;
//    private List<EmailAttachment> attachments;

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

    public List<String> getToEmail() {
        return toEmail;
    }

    public void setToEmail(List<String> toEmail) {
        this.toEmail = toEmail;
    }

    public List<String> getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(List<String> ccEmail) {
        this.ccEmail = ccEmail;
    }

    public List<String> getBccEmail() {
        return bccEmail;
    }

    public void setBccEmail(List<String> bccEmail) {
        this.bccEmail = bccEmail;
    }

//    public List<EmailAttachment> getAttachments() {
//        return attachments;
//    }
//
//    public void setAttachments(List<EmailAttachment> attachments) {
//        this.attachments = attachments;
//    }
}

