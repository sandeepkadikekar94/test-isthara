package com.upliv.user_service.model;

public class SmsModel {
    private String countryCode;
    private String mobile;
    private String message;

    public SmsModel(String countryCode, String mobile, String message) {
        this.countryCode = countryCode;
        this.mobile = mobile;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
