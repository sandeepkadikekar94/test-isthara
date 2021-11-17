package com.upliv.user_service.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateOtpRequest {

    private final Logger logger = LoggerFactory.getLogger(ValidateOtpRequest.class);

    private int otp;
    private String countryCode;
    private String mobile;
    private String email;
    DeviceInfo deviceInfo;

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
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

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
