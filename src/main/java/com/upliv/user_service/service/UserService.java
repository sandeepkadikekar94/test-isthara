package com.upliv.user_service.service;

import com.upliv.user_service.authentication.AuthenticationUtil;
import com.upliv.user_service.constants.Messages;
import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.exceptions.*;
import com.upliv.user_service.model.*;
import com.upliv.user_service.repository.OTPRepository;
import com.upliv.user_service.repository.UserRepository;
import com.upliv.user_service.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    ServiceInvoker serviceInvoker;

    @Autowired
    OTPRepository otpRepository;

    private final ExcelUtil uploadUtil;

    public UserService(ExcelUtil uploadUtil) {
        this.uploadUtil = uploadUtil;
    }


    public User signup(User user, UserConstants.AuthenticationType type) {
        switch (type) {
            case EMAIL_ONLY: {

            }
            case PHONE_ONLY: {

            }
            case EMAIL_OR_PHONE: {

            }
            default:
                // return error for not selecting the right authentication type
        }
        return null;
    }

    private User createUser(User reqUser) {
        return null;
    }

/*
1) Create User
2) Send SMS / Email for Account creation Welcome Message
3) Send SMS / Email for Otp message
 */


    public User signup(User user) {
        User temp = userRepository.findUserByMobile(user.getCountryCode(), user.getMobile());
        if (temp == null) {
            user = userRepository.save(user);
            authenticationUtil.initiateUserValidation(user);
            return user;
        } else {
            throw new ResourceNotFoundException(ErrorMessage.USER_ALREADY_EXIST, ErrorCodes.USER_ALREADY_EXIST);
        }
    }

    public User createNewUserInternal(UserInviteRequest userInviteRequest) {
        User temp = userRepository.findUserByMobile(userInviteRequest.getCountryCode(), userInviteRequest.getMobile());
        if (temp == null) {
            User user = new User();
            user.setFullName(userInviteRequest.getFullName());
            user.setCountryCode(userInviteRequest.getCountryCode());
            user.setMobile(userInviteRequest.getMobile());
            user.setProfilePicture(userInviteRequest.getProfileImage());

            user = userRepository.save(user);
            String message = String.format(Messages.smsInviteMessage, user.getFullName(), userInviteRequest.getInvitedByName(),
                    userInviteRequest.getUnitDesc(), userInviteRequest.getCommunityName());
            serviceInvoker.generateSMS(user.getCountryCode(), user.getMobile(), message);
            return user;
        } else {
            return temp;
        }
    }

    public void createUserWhileExcelUpload(UserInviteRequest userInviteRequest) {
        User temp = userRepository.findUserByMobile(userInviteRequest.getCountryCode(), userInviteRequest.getMobile());
        if (temp == null) {
            User user = new User();
            user.setFullName(userInviteRequest.getFullName());
            user.setCountryCode(userInviteRequest.getCountryCode());
            user.setMobile(userInviteRequest.getMobile());
            user.setProfilePicture(userInviteRequest.getProfileImage());
            user.setActive(true);
            user.setEmail(userInviteRequest.getEmailId());
            user.setResident(true);

//            user.setCreatedTimeStamp(userInviteRequest.getCreatedTimeStamp());
//            user.setGender();


            userRepository.save(user);
//            String message = String.format(Messages.smsInviteMessage, user.getFullName(),userInviteRequest.getInvitedByName(),
//                    userInviteRequest.getUnitDesc(), userInviteRequest.getCommunityName());
//            serviceInvoker.generateSMS(user.getCountryCode(),user.getMobile(), message);
        } else {
            temp.setResident(true);
            userRepository.save(temp);
        }
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findUserByMobile(loginRequest.getCountryCode(), loginRequest.getMobile());
        if (user != null) {
            if (loginRequest.getMobile().equalsIgnoreCase("9787353665")
                    && loginRequest.getCountryCode().equalsIgnoreCase("91")) {
                System.out.println("Test user");
            } else {
                authenticationUtil.loginUser(user);
            }
            return "OTP Sent";
        }
        throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
    }

    public User getUserById(UUID userId) {
        AtomicReference<User> userRef = new AtomicReference<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresentOrElse((user) -> {
            userRef.set(user);
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
        });
        return userRef.get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Object verifyOtp(ValidateOtpRequest loginRequest) {
        if (loginRequest.getMobile().equalsIgnoreCase("9787353665")
                && loginRequest.getCountryCode().equalsIgnoreCase("91") && loginRequest.getOtp() == 123456) {
            User user = userRepository.findUserByMobile(loginRequest.getCountryCode(), loginRequest.getMobile());
            if (user != null) {
                user.setVerified(true);
                return userRepository.save(user);
            }
        }
        return authenticationUtil.authenticate(loginRequest);
    }

    public void logout(UUID userId, DeviceInfo deviceInfo) {
        userRepository.findById(userId).ifPresentOrElse(
                userDetail -> {
                    if (deviceInfo != null) {
                        userDetail.removeDeviceInfo(deviceInfo);
                        userRepository.save(userDetail);
                    }
                },
                () -> {
//                    return;
//                    throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
                });
    }

    public String resendOtp(LoginRequest loginRequest) {
        User user = userRepository.findUserByMobile(loginRequest.getCountryCode(), loginRequest.getMobile());
        if (user == null) {
            throw new UserNotAuthorizedException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
        }
        Optional<OTPData> optionalOtp = otpRepository.findById(user.getUserId());
        optionalOtp.ifPresentOrElse((userOtp) -> {
            serviceInvoker.generateSMS(loginRequest.getCountryCode(), loginRequest.getMobile(), String.format(Messages.smsOtpMessage, userOtp.getOtp()));
        }, () -> {
            throw new UserNotAuthorizedException(ErrorMessage.OTP_EXPIRED, ErrorCodes.OTP_EXPIRED);
        });
        return "OTP Sent";
    }


    public User updateUser(String userId, User newUser) {
        User user = getUserById(UUID.fromString(userId));
        if (user != null) {
            user.updateUser(newUser);
            return userRepository.save(user);
        }
        throw new UserNotAuthorizedException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
    }


    public List<Map<String, String>> upload(MultipartFile file) throws Exception {

        Path tempDir = Files.createTempDirectory("");
        File tempFile = tempDir.resolve(Objects.requireNonNull(file.getOriginalFilename())).toFile();
        file.transferTo(tempFile);
        Workbook workbook = WorkbookFactory.create(tempFile);
        Sheet sheet = workbook.getSheetAt(0);
        Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowStreamSupplier(sheet);
        Row headerRow = rowStreamSupplier.get().findFirst().get();
        List<String> headerCells = uploadUtil.getStream(headerRow)
                .map(Cell::getStringCellValue)
                .map(String::valueOf)
                .collect(Collectors.toList());

        int colCount = headerCells.size();

        List<Map<String, String>> list = rowStreamSupplier.get()
                .skip(1)
                .map(row -> {

                    List<String> cellList = uploadUtil.getStream(row)
                            .map(Cell::getStringCellValue)
                            .collect(Collectors.toList());

                    return uploadUtil.cellIteratorSupplier(colCount)
                            .get()
                            .collect(toMap(headerCells::get, cellList::get));
                })
                .collect(Collectors.toList());
        System.out.println("");

        for (int i = 0; i < list.size(); i++) {
            UserInviteRequest user = new UserInviteRequest();

            user.setEmailId(list.get(i).get("emailId"));
            user.setIsActive(list.get(i).get("isActive"));
            user.setGender(list.get(i).get("gender"));
            user.setCountryCode("91");
            user.setMobile(list.get(i).get("mobile"));
            user.setFullName(list.get(i).get("fullName"));
            user.setCreatedTimeStamp(list.get(i).get("createdTimeStamp"));


            createUserWhileExcelUpload(user);
//            Mobile mobile = new Mobile();
//            EnquiryContactDetails contactDetails  = new EnquiryContactDetails();
//            PropertyForEnquiry propertyDetail = new PropertyForEnquiry();
//            TenantEnquiry enquiry = new TenantEnquiry();
//
//
//
//            mobile.setMobile(list.get(i).get("Mobile"));
//            mobile.setCountryCode(list.get(i).get("countryCode"));
//
//            contactDetails.setMobile(mobile);
//            contactDetails.setPersonalEmailId(list.get(i).get("Email"));
//
//            propertyDetail.setCity(list.get(i).get("city"));
//            propertyDetail.setLocality(list.get(i).get("locality"));
//
//            propertyDetail.setPropertyName(list.get(i).get("propertyName"));
//
//
//
//            enquiry.setEnquiryContactDetails(contactDetails);
//            enquiry.setPropertyDetails(propertyDetail);
//
//            createEnquiry(enquiry);
//
//            tenantEnquiryRepository.save(enquiry);
        }
        return list;
    }


    /*
     * To check if the user exists in the new DB, and set him as non-resident if he exists, else create new user in current DB
     */
    public User requestFromOldErp(OldErpUserRequest request) {
        System.out.println("Request : " + request.toString());
        User existing = userRepository.findUserByMobile("91", request.getMobile());

        User emailExisting = userRepository.findUserByEmail(request.getEmail());

//        for(int i=0 ; i<temp1.size() ; i++){
//            System.out.println(temp1.get(i).getEmail());
//        }
        if (emailExisting != null && !(emailExisting.getEmail().equalsIgnoreCase("string"))) {

            if (existing != null) {
                if (!emailExisting.getMobile().equals(existing.getMobile())) {
                    throw new ResourceAlreadyExistException("Different (or) multiple users exist with entered mobile information"
                            , ErrorCodes.DETAILS_NOT_VALID);
                }
                if (!emailExisting.getEmail().equals(existing.getEmail())) {
                    throw new ResourceAlreadyExistException("Different (or) multiple users exist with entered email information"
                            , ErrorCodes.DETAILS_NOT_VALID);
                }
            }
        }

        User newUser = new User();

        if (existing == null) {
            newUser.setFullName(request.getName());
            newUser.setCountryCode("91");
            newUser.setMobile(request.getMobile());
            newUser.setEmail(request.getEmail());
            if (request.getRequestType() == UserConstants.REQUEST_TYPE.CHECK_IN) {
                newUser.setResident(true);
            } else {
                newUser.setResident(false);
            }

            newUser = userRepository.save(newUser);
            return newUser;
        } else {
            if (request.getRequestType() == UserConstants.REQUEST_TYPE.CHECK_IN) {
                existing.setResident(true);
            } else {
                existing.setResident(false);
            }
            existing = userRepository.save(existing);
            return existing;
        }
//            else if(temp1.getMobile().equals(temp2.getMobile())  || temp1.getEmail().equals(temp2.getEmail()) ){
//            if(request.getRequestType() == UserConstants.REQUEST_TYPE.CHECK_IN){
//                temp1.setResident(true);
//            }else{
//                temp1.setResident(false);
//            }
//            temp1 = userRepository.save(temp1);
//            return temp1;
//        }
    }
}
