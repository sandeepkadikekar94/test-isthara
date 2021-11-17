package com.upliv.user_service.authentication;

import com.upliv.user_service.exceptions.ErrorCodes;
import com.upliv.user_service.exceptions.ErrorMessage;
import com.upliv.user_service.exceptions.UserNotAuthorizedException;
import com.upliv.user_service.model.OTPData;
import com.upliv.user_service.model.User;
import com.upliv.user_service.model.ValidateOtpRequest;
import com.upliv.user_service.repository.OTPRepository;
import com.upliv.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public abstract class AbstractAuthenticator implements Authenticator{
    @Autowired
    UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Override
    public User authenticate(ValidateOtpRequest request) {
        AtomicReference<User> returnVal = new AtomicReference<>();
        if(request.getCountryCode()!=null && request.getMobile() !=null) {
            User user = userRepository.findUserByMobile(request.getCountryCode(), request.getMobile());
            if (user == null) {
                throw new UserNotAuthorizedException(ErrorMessage.INVALID_CREDENTIALS, ErrorCodes.INVALID_CREDENTIALS);
            }
            Optional<OTPData> optionalOtp = otpRepository.findById(user.getUserId());
            optionalOtp.ifPresentOrElse((userOtp) -> {
                Date currentTime = new Date();
                long diff = currentTime.getTime() - userOtp.getCreatedTimestamp().getTime();
                if((diff/1000) > 600) {
                    otpRepository.delete(userOtp);
                    throw new UserNotAuthorizedException(ErrorMessage.OTP_EXPIRED, ErrorCodes.OTP_EXPIRED);
                } else {
                    if (userOtp.getOtp() != request.getOtp()) {
                        throw new UserNotAuthorizedException(String.format(ErrorMessage.INVALID_CREDENTIALS, "OTP"), ErrorCodes.INVALID_CREDENTIALS);
                    }
                    user.setVerified(true);
                    user.addDeviceInfo(request.getDeviceInfo());
                    returnVal.set(userRepository.save(user));
                    otpRepository.delete(optionalOtp.get());
                }
            }, () -> {
                throw new UserNotAuthorizedException(ErrorMessage.OTP_EXPIRED, ErrorCodes.OTP_EXPIRED);
            });
            return user;
        }
        else {
            User user = userRepository.findUserByEmail(request.getEmail());
            if (user == null) {
                throw new UserNotAuthorizedException(ErrorMessage.INVALID_CREDENTIALS, ErrorCodes.INVALID_CREDENTIALS);
            }
            Optional<OTPData> optionalOtp = otpRepository.findById(user.getUserId());
            optionalOtp.ifPresentOrElse((userOtp) -> {
                Date currentTime = new Date();
                long diff = currentTime.getTime() - userOtp.getCreatedTimestamp().getTime();
                if((diff/1000) > 600) {
                    otpRepository.delete(userOtp);
                    throw new UserNotAuthorizedException(ErrorMessage.OTP_EXPIRED, ErrorCodes.OTP_EXPIRED);
                } else {
                    if (userOtp.getOtp() != request.getOtp()) {
                        throw new UserNotAuthorizedException(String.format(ErrorMessage.INVALID_CREDENTIALS, "OTP"), ErrorCodes.INVALID_CREDENTIALS);
                    }
                    user.setVerified(true);
                    user.addDeviceInfo(request.getDeviceInfo());
                    returnVal.set(userRepository.save(user));
                    otpRepository.delete(optionalOtp.get());
                }
            }, () -> {
                throw new UserNotAuthorizedException(ErrorMessage.OTP_EXPIRED, ErrorCodes.OTP_EXPIRED);
            });
            return user;
        }
    }
}
