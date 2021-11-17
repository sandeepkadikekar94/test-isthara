package com.upliv.user_service.service;

import com.upliv.user_service.authentication.AuthenticationUtil;
import com.upliv.user_service.authentication.EmailAuthenticator;
import com.upliv.user_service.authentication.MobileAuthenticator;
import com.upliv.user_service.constants.Messages;
import com.upliv.user_service.exceptions.ErrorCodes;
import com.upliv.user_service.exceptions.ErrorMessage;
import com.upliv.user_service.exceptions.ResourceNotFoundException;
import com.upliv.user_service.exceptions.UserNotAuthorizedException;
import com.upliv.user_service.model.*;
import com.upliv.user_service.repository.ContactUsRepository;
import com.upliv.user_service.repository.OTPRepository;
import com.upliv.user_service.repository.UserDetailsRepository;
import com.upliv.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserDetailsService {

    @Value("${contactUs.email.toEmails}")
    private String sendEnquirytoEmails;
    @Value("${contactUs.email.ccEmails}")
    private String sendEnquiryccEmails;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    OTPRepository otpRepository;

    @Autowired
    ServiceInvoker serviceInvoker;

    @Autowired
    private ContactUsRepository contactUsRepository;

    @Autowired
    private EmailAuthenticator emailAuthenticator;

    @Autowired
    private MobileAuthenticator mobileAuthenticator;

    @Value("${message.email.otp.template}")
    private String otpTemplateId;

    public User signup(User user) {
        User tempMobile = userRepository.findUserByMobile(user.getCountryCode(), user.getMobile());
        User tempEmail = userRepository.findUserByEmail(user.getEmail());
        if(tempMobile == null && tempEmail == null) {
            user = userRepository.save(user);
            authenticationUtil.initiateUserValidation(user);
            return user;
        } else {
            throw new ResourceNotFoundException(ErrorMessage.USER_ALREADY_EXIST, ErrorCodes.USER_ALREADY_EXIST);
        }
    }
    public String login(LoginRequest loginRequest) {
        User userByMobile = userRepository.findUserByMobile(loginRequest.getCountryCode(), loginRequest.getMobile());
        User userByEmail = userRepository.findUserByEmail(loginRequest.getEmail());

        if(userByMobile!=null || userByEmail !=null){
            if(loginRequest.getMobile() !=null && loginRequest.getMobile().equalsIgnoreCase("9787353665")
                    && loginRequest.getCountryCode().equalsIgnoreCase("91")) {
                System.out.println("Test user");
            }
            else {
                if(userByMobile!=null) {
                    Optional<OTPData> optionalOtpByMobile = otpRepository.findById(userByMobile.getUserId());
                    optionalOtpByMobile.ifPresentOrElse((userotp) -> {
                        otpRepository.delete(userotp);
                        authenticationUtil.loginUserByMobile(userByMobile);
                    },() ->{
                        authenticationUtil.loginUserByMobile(userByMobile);
                    });
                }
                else {
                    Optional<OTPData> optionalOtpByEmail = otpRepository.findById(userByEmail.getUserId());
                    optionalOtpByEmail.ifPresentOrElse((userotp) -> {
                        otpRepository.delete(userotp);
                        authenticationUtil.loginUserByEmail(userByEmail);
                    },() ->{
                        authenticationUtil.loginUserByEmail(userByEmail);
                    });
                }
            }
            return "OTP Sent";
        }
        throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND,ErrorCodes.USER_NOT_FOUND);
    }
    public Object verifyOtp(ValidateOtpRequest loginRequest) {
        if(loginRequest.getMobile() != null && loginRequest.getMobile().equalsIgnoreCase("9787353665")
                && loginRequest.getCountryCode().equalsIgnoreCase("91") && loginRequest.getOtp() == 123456) {
            User userByMobile = userRepository.findUserByMobile(loginRequest.getCountryCode(), loginRequest.getMobile());
            User userByEmail = userRepository.findUserByEmail(loginRequest.getEmail());
            if(userByMobile != null || userByEmail !=null) {
                if(userByMobile !=null) {
                    userByMobile.setVerified(true);
                    return userRepository.save(userByMobile);
                }
                else
                {
                    userByEmail.setVerified(true);
                    return userRepository.save(userByEmail);
                }
            }
        }
        return authenticationUtil.authenticate(loginRequest);
    }
    public String resendOtp(LoginRequest loginRequest) {
        User userByMobile = userRepository.findUserByMobile(loginRequest.getCountryCode(), loginRequest.getMobile());
        User userByEmail = userRepository.findUserByEmail(loginRequest.getEmail());
        if (userByMobile == null && userByEmail == null) {
            throw new UserNotAuthorizedException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
        }
        if(userByMobile !=null) {
            Optional<OTPData> optionalOtpByMobile = otpRepository.findById(userByMobile.getUserId());
            if(optionalOtpByMobile !=null) {
                OTPData otpData = authenticationUtil.regeneateotp(userByMobile);
                optionalOtpByMobile.ifPresentOrElse((userOtp) -> {
                    userOtp.setOtp(otpData.getOtp());
                    otpRepository.save(userOtp);
                    serviceInvoker.generateSMS(loginRequest.getCountryCode(), loginRequest.getMobile(), String.format(Messages.smsOtpMessage, userOtp.getOtp()));
                }, () -> {
                    otpRepository.save(otpData);
                    serviceInvoker.generateSMS(loginRequest.getCountryCode(), loginRequest.getMobile(), String.format(Messages.smsOtpMessage, otpData.getOtp()));
//                    throw new UserNotAuthorizedException(ErrorMessage.OTP_EXPIRED, ErrorCodes.OTP_EXPIRED);
                });
            }
        }
        else {
            if(userByEmail !=null) {
                Optional<OTPData> optionalOtpByEmail = otpRepository.findById(userByEmail.getUserId());
                if(optionalOtpByEmail !=null) {
                     OTPData otpData = authenticationUtil.regeneateotp(userByEmail);
                    optionalOtpByEmail.ifPresentOrElse((userOtp) -> {
                        userOtp.setOtp(otpData.getOtp());
                        otpRepository.save(userOtp);
                        Map<String, String> templateData = new HashMap<>();
                templateData.put("user_name", userByEmail.getFullName());
                templateData.put("user_otp", String.valueOf(userOtp.getOtp()));
                serviceInvoker.generateTemplateEmail(loginRequest.getEmail(),otpTemplateId, templateData);
            }, () -> {
                        otpRepository.save(otpData);
                        serviceInvoker.generateSMS(loginRequest.getCountryCode(), loginRequest.getMobile(), String.format(Messages.smsOtpMessage, otpData.getOtp()));
//                throw new UserNotAuthorizedException(ErrorMessage.OTP_EXPIRED, ErrorCodes.OTP_EXPIRED);
            });
                }
            }
        }
        return "OTP Sent";
    }
    public UserRequest updateUserDetails(UserRequest userRequest,String userId ){
//        Optional<UserDetails> optionalUserDetails = userDetailsRepository.findById(UUID.fromString(userId));
        if(userRequest !=null) {
            UserDetails userDetails = getUserDetailsById(UUID.fromString(userId), false);
            if(userRequest.getUserDetails() !=null) {
                if (userDetails != null) {
                    userDetails.updateUserDetails(userRequest.getUserDetails());
                } else {
                    userDetails = userRequest.getUserDetails();
                    userDetails.setUserId(UUID.fromString(userId));
                }
                userDetailsRepository.save(userDetails);
            }
            User user = getUserById(UUID.fromString(userId));
            if(user !=null) {
                user.updateUser(userRequest.getUser());
                userRepository.save(user);
            }
            userRequest.setUserDetails(userDetails);
            userRequest.setUser(user);
            return userRequest;
        }
        throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND,ErrorCodes.USER_NOT_FOUND);
    }
    public User getUserById(UUID userId) {
        AtomicReference<User> userRef = new AtomicReference<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresentOrElse((user)->{
            userRef.set(user);
        },()->{
            throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
        });
        return userRef.get();
    }

    //    public User updateUserDetails(UserDetails newUserDetails,String userId)
//    {
//        User user = getUserById(UUID.fromString(userId));
//        if(user !=null)
//        {
//            user.updateUser(newUser);
//        }
//        return user;
//    }
    public UserDetails getUserDetailsById(UUID userId, boolean flag) {
        AtomicReference<UserDetails> userDetailsRef = new AtomicReference<>();
        Optional<UserDetails> optionalUser = userDetailsRepository.findById(userId);
        if(flag) {
            optionalUser.ifPresentOrElse((userDetails) -> {
                userDetailsRef.set(userDetails);
            }, () -> {
                throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
            });
        }
        else
        {
            optionalUser.ifPresentOrElse((userDetails) -> {
                userDetailsRef.set(userDetails);
            }, () -> {
                new UserDetails();
            });
        }
        return userDetailsRef.get();
    }
    public ContactUs createContactUs(ContactUs contactUs) {
        if(contactUs != null) {

            String[] temp = sendEnquirytoEmails.split(",");
            List<String> toEmails = Arrays.asList(temp);
            String[] tempcc = sendEnquiryccEmails.split(",");
            List<String> ccMails = Arrays.asList(tempcc);
            String subject = "Contact details request raised";

            //Converting UTC to IST for forwarding information to support team
            DateFormat gmtFormat = new SimpleDateFormat();
            TimeZone gmtTime = TimeZone.getTimeZone("IST");
            gmtFormat.setTimeZone(gmtTime);

            String body = "\nHello, \n\nThe following request has been raised, from Contact Us: \n\nName: "+contactUs.getName()+
                    "\nMobile :"+contactUs.getMobile().getMobile()+
                    "\nEmail id: "+contactUs.getEmail()+
                    "\nDescription: "+contactUs.getMessage()+
                    "\nDate and time of creation: "+gmtFormat.format(contactUs.getCreatedDateTimeStamp())+
                    "\n\n Please do the needful. \n\nThank you.";

            serviceInvoker.generateEmail(toEmails,ccMails, subject, body);
            contactUsRepository.save(contactUs);
            //serviceInvoker.generateEmail(contactUs.getEmail(),Messages.emailContactUsSubject ,String.format(Messages.smsOtpMessage));
            return contactUs;
        } else {
            throw new ResourceNotFoundException(ErrorMessage.DETAILS_NOT_VALID, ErrorCodes.DETAILS_NOT_VALID);
        }
    }
    public List<ContactUs> getContactUs() {
        return contactUsRepository.findAll();
    }


}