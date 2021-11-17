package com.upliv.user_service.authentication;

import com.upliv.user_service.constants.Messages;
import com.upliv.user_service.model.OTPData;
import com.upliv.user_service.model.User;
import com.upliv.user_service.repository.OTPRepository;
import com.upliv.user_service.service.ServiceInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("mobileAuthenticator")
@Primary
public class MobileAuthenticator extends AbstractAuthenticator {

    @Autowired
    private ServiceInvoker serviceInvoker;

    @Autowired
    private OTPRepository otpRepository;

    private final Logger logger = LoggerFactory.getLogger(MobileAuthenticator.class);

    @Override
    public void initiateUserValidation(User endUser, OTPData userOtp) {
        //Generate OTP to be validated
        //send SMS to the respective user
        String mobile = endUser.getMobile();
        if (mobile != null) {
            serviceInvoker.generateSMS(endUser.getCountryCode(), mobile , String.format(Messages.smsOtpMessage, userOtp.getOtp()));
            serviceInvoker.generateSMS(endUser.getCountryCode(), mobile , String.format(Messages.smsWelcomeMessage, endUser.getFullName(), "www.isthara.com"));
        } else {
            logger.error("Unable to generate SMS as mobile number is not provided for user");
        }
    }

    @Override
    public void login(User endUser, OTPData userOtp) {
        String mobile = endUser.getMobile();
        if (mobile != null) {
            serviceInvoker.generateSMS(endUser.getCountryCode(), mobile , String.format(Messages.smsOtpMessage, userOtp.getOtp()));
        } else {
            logger.error("Unable to generate SMS as mobile number is not provided for user");
        }
    }

}
