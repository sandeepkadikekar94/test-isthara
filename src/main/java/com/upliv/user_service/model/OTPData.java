package com.upliv.user_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Document
public class OTPData {

    @Id
    private UUID userId;
    private int otp;
    private Date createdTimestamp;

    public OTPData(UUID userId) {
        this.userId = userId;
        this.generateOtp();
        this.createdTimestamp = new Date();
    }

    /**
     * This method is responsible for a 6 digit OTP generation
     */
    public void generateOtp(){
        int number = ThreadLocalRandom.current().nextInt(100000, 1000000);
        // this will convert any number sequence into 6 character.
        this.otp =  number;

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}
