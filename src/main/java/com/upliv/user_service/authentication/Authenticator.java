package com.upliv.user_service.authentication;

import com.upliv.user_service.model.OTPData;
import com.upliv.user_service.model.User;
import com.upliv.user_service.model.ValidateOtpRequest;

public interface Authenticator {
    User authenticate(ValidateOtpRequest request);
    void initiateUserValidation(User endUser, OTPData userOtp);
    void login(User endUser, OTPData userOtp);
}
