package com.upliv.user_service.model.staff;


import com.upliv.user_service.model.common.EmailRequest;
import com.upliv.user_service.model.common.SmsRequest;

public class StaffNotification {

    private SmsRequest smsDetails;
    private EmailRequest emailDetails;

    public SmsRequest getSmsDetails() {
        return smsDetails;
    }

    public void setSmsDetails(SmsRequest smsDetails) {
        this.smsDetails = smsDetails;
    }

    public EmailRequest getEmailDetails() {
        return emailDetails;
    }

    public void setEmailDetails(EmailRequest emailDetails) {
        this.emailDetails = emailDetails;
    }


}
