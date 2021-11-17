package com.upliv.user_service.authentication;

import com.upliv.user_service.model.OTPData;
import com.upliv.user_service.model.User;
import com.upliv.user_service.repository.OTPRepository;
import com.upliv.user_service.service.ServiceInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("emailAuthenticator")
public class EmailAuthenticator extends AbstractAuthenticator {

    @Autowired
    private ServiceInvoker serviceInvoker;

    @Autowired
    private OTPRepository otpRepository;

    @Value("${message.email.welcome.template}")
    private String welcomeTemplateId;

    @Value("${message.email.otp.template}")
    private String otpTemplateId;

    private final Logger logger = LoggerFactory.getLogger(EmailAuthenticator.class);

    @Override
    public void initiateUserValidation(User endUser, OTPData userOtp) {
        //Generate OTP to be validated
        //send SMS to the respective user
        String mailId = endUser.getEmail();

        if (mailId != null) {
            Map<String, String> templateData = new HashMap<>();
            templateData.put("user_name", endUser.getFullName());
            templateData.put("user_otp", String.valueOf(userOtp.getOtp()));
            serviceInvoker.generateTemplateEmail(mailId, welcomeTemplateId, templateData);
            serviceInvoker.generateTemplateEmail(mailId, otpTemplateId, templateData);
        } else {
            logger.error("Unable to generate email as email id not provided for user");
        }
    }

    @Override
    public void login(User endUser, OTPData userOtp) {
        String mailId = endUser.getEmail();
        if (mailId != null) {
            Map<String, String> templateData = new HashMap<>();
            templateData.put("user_name", endUser.getFullName());
            templateData.put("user_otp", String.valueOf(userOtp.getOtp()));
            serviceInvoker.generateTemplateEmail(mailId, otpTemplateId, templateData);
        }
    }

}
