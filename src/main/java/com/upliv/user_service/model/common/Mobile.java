package com.upliv.user_service.model.common;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Mobile {
    @NotNull
    @NotEmpty
    private String countryCode;
    @NotNull
    @NotEmpty
    private String mobile;

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
}
