package com.upliv.user_service.authentication;


import com.upliv.user_service.model.OTPData;
import com.upliv.user_service.model.User;
import com.upliv.user_service.model.ValidateOtpRequest;
import com.upliv.user_service.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationUtil {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    @Qualifier("emailAuthenticator")
    private EmailAuthenticator emailAuthenticator;

    @Autowired
    @Qualifier("mobileAuthenticator")
    private MobileAuthenticator mobileAuthenticator;

    @Autowired
    private AbstractAuthenticator abstractAuthenticator;

    public void initiateUserValidation(User user) {
        OTPData userOtp = generateOtp(user);
        emailAuthenticator.initiateUserValidation(user, userOtp);
        mobileAuthenticator.initiateUserValidation(user, userOtp);
    }

    private OTPData generateOtp(User user ){
        Optional<OTPData> optionalOtp = otpRepository.findById(user.getUserId());
        OTPData userOtp = null;
        if(!optionalOtp.isPresent()) {
            userOtp = new OTPData(user.getUserId());
        }else{
            userOtp = optionalOtp.get();
        }
        otpRepository.save(userOtp);
        return userOtp;
    }

    public void loginUser(User user){
        OTPData userOtp = generateOtp(user);
        emailAuthenticator.login(user,userOtp);
        mobileAuthenticator.login(user,userOtp);
    }

    public Object authenticate(ValidateOtpRequest loginDetail) {
        return abstractAuthenticator.authenticate(loginDetail);
    }

    public void loginUserByMobile(User user){
        OTPData userOtp = generateOtp(user);
        mobileAuthenticator.login(user,userOtp);
    }
    public void loginUserByEmail(User user){
        OTPData userOtp = generateOtp(user);
        emailAuthenticator.login(user,userOtp);
    }
    public OTPData regeneateotp(User user) {
        OTPData userOtp = new OTPData(user.getUserId());
        return userOtp;
    }
}
