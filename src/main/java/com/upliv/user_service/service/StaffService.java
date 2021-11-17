package com.upliv.user_service.service;

import com.upliv.user_service.constants.StaffConstants;
import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.exceptions.*;
import com.upliv.user_service.model.common.EmailRequest;
import com.upliv.user_service.model.common.EmergencyContact;
import com.upliv.user_service.model.staff.*;
import com.upliv.user_service.model.staff.attendance.Attendance;
import com.upliv.user_service.model.staff.attendance.DailyAttendance;
import com.upliv.user_service.rbac.models.ModulePermissions;
import com.upliv.user_service.rbac.models.ResourcePermissions;
import com.upliv.user_service.rbac.models.RoleConstants;
import com.upliv.user_service.rbac.models.UserPermissions;
import com.upliv.user_service.rbac.service.RbacService;
import com.upliv.user_service.repository.StaffAttendanceRepository;
import com.upliv.user_service.repository.StaffDetailsRepository;
import com.upliv.user_service.repository.StaffRepository;
import com.upliv.user_service.utils.DbSequenceHelper;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StaffService {

    @Value("${staffCredentials.email.toEmails}")
    private String sendCredentialstoEmails;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffDetailsRepository staffDetailsRepository;

    @Autowired
    private StaffAttendanceRepository staffAttendanceRepository;

    @Autowired
    private RbacService rbacService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private DbSequenceHelper dbSequenceHelper;

    @Autowired
    ServiceInvoker serviceInvoker;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Staff createStaff(Staff staff) {
        Staff ref = staffRepository.findByMobile(staff.getContactDetails().getMobile().getMobile());
        if(ref != null){
            throw new ResourceAlreadyExistException(ErrorMessage.USER_ALREADY_EXIST, ErrorCodes.USER_ALREADY_EXIST);
        } else {
            Timeline timeline = new Timeline();
            timeline.setActionType(StaffConstants.timelineActions.STAFF_CREATED);
            timeline.setHeading("Status Created");
            timeline.setDescription(StaffConstants.timelineActions.STAFF_CREATED.getDesc());
            timeline.setActionTimestamp(new Date());

            Attendance attendanceRef = new Attendance();
            attendanceRef.setStaffId(staff.getStaffId());
            if(staff.getStaffDetails().getStaffProfessionalDetails() != null) {
                staff.getStaffDetails().getStaffProfessionalDetails().getResignationDetails().setResigned(false);
            }
            staffAttendanceRepository.save(attendanceRef);

            LoginCredentials loginCredentials = new LoginCredentials();
            loginCredentials.generateCredentials(staff.getContactDetails().getMobile().getMobile());
            staff.setLoginCredentials(loginCredentials);
            System.out.println("Username : " + loginCredentials.getUsername() + "  PwD : "  + loginCredentials.getPassword());

            staff.setResetPassword(true);
            int displayCount = dbSequenceHelper.getNextSequenceValue(UserConstants.DB_SEQUENCE_TYPE.STAFF);
            String displayId = UserConstants.BRAND_NAME + "-";
            if (displayCount < 100) {
                displayId += String.format("%03d", displayCount);
            } else {
                displayId += String.valueOf(displayCount);
            }
            staff.setDisplayId(displayId);
            staff.setActive(true);

            //To set default active status to true and verification status as verified
            List<KycDetails> temp = staff.getStaffDetails().getKycDetails();
            Iterator<KycDetails> iterator = temp.listIterator();
            while (iterator.hasNext()){
                KycDetails obj = iterator.next();
                obj.setActive(true);
                obj.setStatus(StaffConstants.VERIFICATION_STATUS.VERIFIED);
            }

            staff.getStaffDetails().setId(staff.getStaffId());
            staffDetailsRepository.save(staff.getStaffDetails());

            /*
             *  Sending new credentials to personal mail of staff
             */
            List mailsList = new ArrayList();
            mailsList.add(staff.getContactDetails().getPersonalEmailId());
            mailsList.add(staff.getContactDetails().getWorkEmailId());

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setToEmail(mailsList);
            emailRequest.setSubject("Welcome to Isthara");
            emailRequest.setBody("\nGreetings! "+staff.getContactDetails().getName()+",\n\n Your login credentials are" +
                    "\nUsername: "+loginCredentials.getUsername()+
                    "\nPassword: "+loginCredentials.getPassword()+
                    "\n\nNote: " +
                    "Please update your password after logging in");
            StaffNotification staffNotification = new StaffNotification();
            staffNotification.setEmailDetails(emailRequest);

            String mailId = staff.getContactDetails().getPersonalEmailId();

            //Sending email to personal email of the staff only
            notifyStaff(staff.getStaffId(), false,true, staffNotification, mailId);

            String encrypted = passwordEncoder.encode(staff.getLoginCredentials().getPassword());
            staff.getLoginCredentials().setPassword(encrypted);
            return staffRepository.save(staff);
        }
    }

    //Generates new login credentials for a staff
    public String generateCredentials(UUID staffId){

        Optional<Staff> optionalStaff = staffRepository.findById(staffId);
        optionalStaff.ifPresentOrElse( ref -> {
            StaffNotification req = new StaffNotification();
            EmailRequest emailRequest = new EmailRequest();
            List<String> toMails = new ArrayList<>();
            if(TextUtils.isBlank(ref.getContactDetails().getWorkEmailId())) {
                toMails.add(ref.getContactDetails().getWorkEmailId());
            }
            if(TextUtils.isBlank(ref.getContactDetails().getPersonalEmailId())) {
                toMails.add(ref.getContactDetails().getPersonalEmailId());
            }
            emailRequest.setToEmail(toMails);

            emailRequest.setBody(" Hello, \nYour login credentials are: \nUsername: "+
                    ref.getLoginCredentials().getUsername()+
                    "\nPassword: "+ ref.getLoginCredentials().getPassword());

            emailRequest.setSubject("\nIsthara login credentials");
            req.setEmailDetails(emailRequest);
            String mailId = ref.getContactDetails().getWorkEmailId();
            notifyStaff(ref.getStaffId(), false, true,req, mailId);
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
        });

        return "New login credentials have been generated successfully ";
    }

    public Staff deleteStaff(UUID staffId){

        Timeline timeline = new Timeline();
        timeline.setActionType(StaffConstants.timelineActions.STAFF_REMOVED);
        timeline.setHeading("Status Changed");
        timeline.setDescription(StaffConstants.timelineActions.STAFF_REMOVED.getDesc());
        timeline.setActionTimestamp(new Date());

        AtomicReference<Staff> staffAtomicReference = new AtomicReference<>();
        Optional<Staff> optionalStaff = staffRepository.findById(staffId);
        optionalStaff.ifPresentOrElse( ref -> {
            ref.getStaffDetails().addAction(timeline);
            ref.setActive(false);
            staffAtomicReference.set(ref);
            staffRepository.save(ref);

            Optional<Attendance> att = staffAttendanceRepository.findById(staffId);
            att.ifPresent(attRef -> {
                attRef.setStatus(StaffConstants.EMPLOYMENT_STATUS.INACTIVE);
                staffAttendanceRepository.save(attRef);
            });
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
        });
        return staffAtomicReference.get();
    }

    /*
     * Not used anywhere
     */
    public Staff getOneStaff(UUID staffId){
        AtomicReference<Staff> staffAtomicReference = new AtomicReference<>();
        Optional<Staff> staffOptional = staffRepository.findById(staffId);
        staffOptional.ifPresentOrElse( ref -> {
            staffAtomicReference.set(ref);
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
        });
        return staffAtomicReference.get();
    }

    //Used for updating all the staff details, we need Admin access for updating these details
    public Staff updateStaff(UpdateStaff request) {

        Timeline timeline = new Timeline();
        timeline.setActionType(request.getStaffStatus().getStatus());
        timeline.setHeading("Staff Details Updated");
        timeline.setDescription("Status changed : " + request.getStaffStatus().getStatus().getDesc());
        timeline.setActionTimestamp(new Date());

        AtomicReference<Staff> staffRef = new AtomicReference<>();
        Optional<Staff> optionalStaffRef = staffRepository.findById(request.getStaff().getStaffId());
        optionalStaffRef.ifPresentOrElse(ref -> {

            //TODO Save staff details - completed
            /*
             * Saving staffDetails in staff details repository
             */
            AtomicReference<StaffDetails> staffDetailsRef = new AtomicReference<>();
            Optional<StaffDetails> optionalStaffDetails = staffDetailsRepository.findById(request.getStaff().getStaffId());
            optionalStaffDetails.ifPresentOrElse( nestedRef -> {
                nestedRef.updateStaffDetails(request.getStaff().getStaffDetails());
                staffDetailsRef.set(staffDetailsRepository.save(nestedRef));
            }, () -> {
                throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
            });

            ref.updateStaffDetails(request.getStaff());
            ref.getStaffDetails().addAction(timeline);
            staffRef.set(staffRepository.save(ref));
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
        });
        return staffRef.get();
    }

    public Staff updateStaffBasic(UUID staffId, UpdateStaffBasic request, boolean contact, boolean emergencyContact, boolean kyc) {

        Timeline timeline = new Timeline();
        timeline.setActionType(request.getStaffStatus().getStatus());
        timeline.setHeading("Staff Basic Details Updated");
        timeline.setDescription("Status changed : " + request.getStaffStatus().getStatus().getDesc());
        timeline.setActionTimestamp(new Date());

        AtomicReference<Staff> atomicStaff = new AtomicReference<>();
        Optional<Staff> optionalStaff = staffRepository.findById(staffId);
        optionalStaff.ifPresentOrElse( tempStaff -> {

            AtomicReference<StaffDetails> atomicStaffDetails = new AtomicReference<>();
            Optional<StaffDetails> optionalStaffDetails = staffDetailsRepository.findById(staffId);
            if(contact){
                tempStaff.getContactDetails().updateContactDetails(request.getStaffBasic().getContactDetails());
                atomicStaff.set(staffRepository.save(tempStaff));
//                System.out.println("Contact details updated");
            }

            //Updating details in StaffDetails Repository
            optionalStaffDetails.ifPresentOrElse(tempStaffDetails -> {
                if(emergencyContact){
                    tempStaffDetails.getEmergencyContactDetails().updateEmergencyContact(request.getStaffBasic().getEmergencyContact());
                    tempStaff.getStaffDetails().getEmergencyContactDetails().updateEmergencyContact(request.getStaffBasic().getEmergencyContact());

                    atomicStaffDetails.set(staffDetailsRepository.save(tempStaffDetails));
                    atomicStaff.set(staffRepository.save(tempStaff));
//                    System.out.println("Emergency contact details updated");
                }
                if(kyc){

//                    Updating single Kyc detail
//                    List<KycDetails> tempList = tempStaffDetails.getKycDetails();
//                    Iterator<KycDetails> i = tempList.listIterator();
//
//                    while (i.hasNext()){
//                            KycDetails obj = i.next();
//                            System.out.println(obj.getDocument_type()+" and "+newObj.getStaffBasic().getKycDetails().getDocument_type());
//
//                            if(obj.getDocument_type().equals(request.getStaffBasic().getKycDetails().getDocument_type())){
//                                obj.setActive(false);
//                            }
//                        }
//                    request.getStaffBasic().getKycDetails().setStatus(StaffConstants.VERIFICATION_STATUS.VERIFIED);
//                    request.getStaffBasic().getKycDetails().setActive(true);
//                    tempList.add(request.getStaffBasic().getKycDetails());

                    //Updating list of Kyc details
                    List<KycDetails> tempList = tempStaffDetails.getKycDetails();
                    Iterator<KycDetails> i = tempList.listIterator();

                    List<KycDetails> newList = request.getStaffBasic().getKycDetails();
                    Iterator<KycDetails> j = newList.listIterator();

                    while(j.hasNext()){
                        KycDetails newObj = j.next();
                        while (i.hasNext()){
                            KycDetails obj = i.next();
//                            System.out.println(obj.getDocument_type()+" and "+newObj.getDocument_type());

                            if(obj.getDocument_type().equals(newObj.getDocument_type())){
                                obj.setActive(false);
                            }
                        }
                        newObj.setStatus(StaffConstants.VERIFICATION_STATUS.VERIFIED);
                        newObj.setActive(true);
                    }


                    for(int x=0 ; x<request.getStaffBasic().getKycDetails().size() ; x++){
                        tempList.add(request.getStaffBasic().getKycDetails().get(x));
                    }

//                    for(KycDetails tmp : tempList){
//                        if(tmp.getDocument_type().equals(request.getStaffBasic().getKycDetails().getDocument_type())){
//                            tempList.remove(tmp);
//                           }
//                            tempList.add(request.getStaffBasic().getKycDetails());
//
//                    }

                    tempStaffDetails.setKycDetails(tempList);
                    tempStaff.getStaffDetails().setKycDetails(tempList);

                    atomicStaffDetails.set(staffDetailsRepository.save(tempStaffDetails));
                    atomicStaff.set(staffRepository.save(tempStaff));
//                    System.out.println("KYC details updated");
                }

            }, () -> {
                throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
            });
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
        });
        return atomicStaff.get();

    }

    public List<Staff> getFilteredStaff(UUID staffId, String city, String locality, String propertyName, String department, String employmentType, Boolean status, String alldetails, String pageNo, String size){

        int page = Integer.parseInt(pageNo);
        int siz = Integer.parseInt(size);
        Sort sort = Sort.by("ProfessionalDetails.WorkDetails.EmploymentType");
        Pageable pageable;
        Query query;

        if(page != 0 && siz != 0 ){
            pageable = PageRequest.of(page, siz, sort);
            query = new Query().with(pageable);
        }else{
            query = new Query();
        }


        List<Criteria> filterCriteria = new ArrayList<>();

        if(staffId != null){
            filterCriteria.add(Criteria.where("StaffId").is(staffId));
        }

        if(city!= null && !city.isEmpty()){
            filterCriteria.add(Criteria.where("ProfessionalDetails.WorkDetails.CityAssigned").is(city));
        }
        if(locality != null && !locality.isEmpty()){
            filterCriteria.add(Criteria.where("ProfessionalDetails.WorkDetails.Locality").is(locality));
        }
        if(propertyName != null && !propertyName.isEmpty()){
            filterCriteria.add(Criteria.where("ProfessionalDetails.WorkDetails.PropertyName").is(propertyName));
        }
        if(department != null && !department.isEmpty()){
            filterCriteria.add(Criteria.where("ProfessionalDetails.WorkDetails.Department").is(department));
        }
        if(employmentType != null && !employmentType.isEmpty()){
            filterCriteria.add(Criteria.where("ProfessionalDetails.WorkDetails.EmploymentType").is(employmentType));
        }
        if(status != null){
            filterCriteria.add(Criteria.where("ProfessionalDetails.WorkDetails.IsActive").is(status));
        }

        if(!filterCriteria.isEmpty() && alldetails != null && alldetails.equals("false")){
            query.fields().exclude("staffDetails");
            query.addCriteria(new Criteria().orOperator(filterCriteria.toArray(new Criteria[filterCriteria.size()])));
            return mongoTemplate.find(query, Staff.class);
        }

        return mongoTemplate.find(query, Staff.class);
    }

    public List<Staff> getStaff(UUID staffId,boolean allDetails){

//        Sort sort = Sort.by("createTimeStamp");
//        Pageable pageable = PageRequest.of(pageNo, size, sort);
//        Query query = new Query().with(pageable);

//        Pageable pageable = PageRequest.of(sort);

        if (staffId != null){
            List<Staff> staffRef = new ArrayList<>();
            Optional<Staff> ref = staffRepository.findById(staffId);
            if(ref != null){
                staffRef.add(ref.get());
                return staffRef;
            }else{
                throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
            }
        }else{
            if(allDetails) {
                return staffRepository.findActiveStaffDetails();
            } else {
                return staffRepository.findActiveStaff();
            }
        }
    }


    public String notifyStaff(UUID staffId, boolean sms, boolean email, StaffNotification request, String details){
//        AtomicReference<Staff> staffAtomicReference = new AtomicReference<>();
        Optional<Staff> staffRef = staffRepository.findById(staffId);
        String result = "";

        /*
         * Used for sending notification when notification is sent to staff when created
         */
        if(staffRef == null){
            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
        }

        /*
         * Used for sending notification for a selected staffId
         */
        if(staffRef != null){

            if(email){
                List<String> toMail = new ArrayList<>();
                if(details != null){
                    toMail.add(details);
                }

                String tmp = staffRef.get().getContactDetails().getWorkEmailId();
                toMail.add(tmp);

                if(request.getEmailDetails().getBccEmail() != null){ // && !(request.getEmailDetails().getBccEmail()).equals("string")){
                    toMail.addAll(request.getEmailDetails().getCcEmail());
                }
                if(request.getEmailDetails().getCcEmail() != null){ // && !(request.getEmailDetails().getCcEmail()).equals("string")){
                    toMail.addAll(request.getEmailDetails().getBccEmail());
                }
                if(request.getEmailDetails().getToEmail() != null){
                    toMail.addAll(request.getEmailDetails().getToEmail());
                }


                String[] temp = sendCredentialstoEmails.split(",");
                toMail.addAll(Arrays.asList(temp));

                serviceInvoker.generateEmail(toMail, null,
                        request.getEmailDetails().getSubject(), request.getEmailDetails().getBody());
                result = result + "\nEmail sent successfully";
            }
            if(sms){
                serviceInvoker.generateSMS( request.getSmsDetails().getCountryCode(),
                        request.getSmsDetails().getMobile(), request.getSmsDetails().getMessage());
                result = result + "\nSms sent successfully";
//                System.out.println(result);
            }
        }

        return result;
    }
//      Sending notification to staff - method 2
//    public String notifyStaffRequest(UUID staffId, boolean sms, boolean email, StaffNotificationRequest request){
//        AtomicReference<Staff> staffAtomicReference = new AtomicReference<>();
//        Optional<Staff> staffRef = staffRepository.findById(staffId);
//        String result = "";
//
//        /*
//         * Used for sending notification when notification is sent to staff when created
//         */
//        if(staffRef == null){
//            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
//        }
//
//        /*
//         * Used for sending notification for a selected staffId
//         */
//        if(staffRef != null){
//
//            if(email){
//                serviceInvoker.generateEmail(request.getToEmailId(),
//                        request.getSubject(), request.getBody());
//                result = result + "\nEmail sent successfully";
////                    System.out.println(result);
//            }
//            if(sms){
//                serviceInvoker.generateSMS( request.getSmsRequest().getCountryCode(),
//                        request.getSmsRequest().getMobile(), request.getSmsRequest().getMessage());
//                result = result + "\nSms sent successfully";
////                System.out.println(result);
//            }
//        }
//
//        return result;
//    }

    /*
     FUNCTION TO ADD DAILY ATTENDANCE
     */
//    public Attendance addDailyAttendance(UUID staffId, DailyAttendance request){
//
//        List<DailyAttendance> list1 = new ArrayList<>();
//        AtomicReference<Attendance> attendanceRef = new AtomicReference<>();
//        Optional<Attendance> optionalAttendance = staffAttendanceRepository.findById(staffId);
//        optionalAttendance.ifPresentOrElse(ref -> {
//            if(ref.getDailyAttendance() == null){
//                list1.add(request);
//                ref.setDailyAttendance(list1);
//            }else{
//                list1.addAll(ref.getDailyAttendance());
//                list1.add(request);
//                ref.setDailyAttendance(list1);
//            }
//            attendanceRef.set(staffAttendanceRepository.save(ref));
//        }, () -> {
//          throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
//        });
//
//        return attendanceRef.get();
//    }

    /*
     * This function is to add day wise attendance to a particular staff which also checks if the date that is being entered is already
     * present in the database or not (does not allow adding multiple attendance records for the same day)
     */
    public Attendance addDailyAttendance(UUID staffId, DailyAttendance request){

        List<DailyAttendance> list1 = new ArrayList<>();
        AtomicReference<Attendance> attendanceRef = new AtomicReference<>();
        Optional<Attendance> optionalAttendance = staffAttendanceRepository.findById(staffId);
        optionalAttendance.ifPresentOrElse(ref -> {
            if(ref.getDailyAttendance() == null){
                list1.add(request);
                ref.setDailyAttendance(list1);
            }else{
                List<DailyAttendance> listRef = ref.getDailyAttendance();
                for(DailyAttendance temp : listRef){
                    Date date1 = temp.getDateForAttendance();
                    Date date2 = request.getDateForAttendance();
                    if(isSameDay(date1, date2)){
                        throw new ResourceAlreadyExistException(ErrorMessage.USER_ALREADY_EXIST, ErrorCodes.DETAILS_NOT_VALID);
                    }
                }
                list1.addAll(ref.getDailyAttendance());
                list1.add(request);
                ref.setDailyAttendance(list1);
            }
            attendanceRef.set(staffAttendanceRepository.save(ref));
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
        });
        return attendanceRef.get();
    }

    /*
     * This function gives filters to retrieve documents based on the values provided
     */
    public List<Attendance> filteredAttendance(UUID staffId, int pageNo, int size, StaffConstants.MONTHS_FOR_ATTENDANCE month, StaffConstants.EMPLOYMENT_STATUS status){

        Sort sort = Sort.by("DailyAttendance.Month");
        Pageable pageable;
        if(pageNo != 0 && size != 0 ){
            pageable = PageRequest.of(pageNo, size, sort);
        }else{
            pageable = PageRequest.of(pageNo, size, sort);
        }
        Query query = new Query().with(pageable);

        List<Criteria> filterCriteria = new ArrayList<>();

        if(staffId != null){
            filterCriteria.add(Criteria.where("StaffId").is(staffId));
        }
        if(month != null){
            filterCriteria.add(Criteria.where("DailyAttendance.Month").is(month));
            System.out.println(month);
        }
        if(status != null){
            filterCriteria.add(Criteria.where("Status").is(StaffConstants.EMPLOYMENT_STATUS.ACTIVE));
        }

//        System.out.println(filterCriteria.toString());

        if(filterCriteria != null && !filterCriteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(filterCriteria.toArray(new Criteria[filterCriteria.size()])));
            System.out.println(query);
        }

        List<Attendance> atoRef = new ArrayList<>();
        atoRef = mongoTemplate.find(query, Attendance.class);
        return  atoRef;
    }


    /*
     * This function calculates the attendance percentage for a give staff id and a given month
     */
    public Attendance getAttendancePercentage(UUID id, Month month, float maxWorkingDays){

        AtomicReference<Attendance> atomicRef = new AtomicReference<>();
        Optional<Attendance> optionalRef = staffAttendanceRepository.findById(id);
        optionalRef.ifPresentOrElse(ref -> {
            List<DailyAttendance> list1 = ref.getDailyAttendance();
            float actual = list1.size();
            float percentage = (actual/maxWorkingDays)*100;
            ref.setAttendancePercentage(percentage);
            atomicRef.set(ref);
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.RECORD_NOT_FOUND, ErrorCodes.RECORD_NOT_FOUND);
        });
        return atomicRef.get();
    }

    //This function is called in the add daily attendance function, this is to check if the date already exists in the database
    public boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }


    public Staff login(StaffLoginRequest request) {
        Staff staff = staffRepository.getStaffByUserName(request.getUserName());
        if (staff == null) {
            throw new UserNotAuthorizedException(ErrorMessage.INVALID_CREDENTIALS, ErrorCodes.INVALID_CREDENTIALS);
        }
        boolean isPwdMatched = passwordEncoder.matches(request.getPassword(), staff.getLoginCredentials().getPassword());
        if(isPwdMatched) {
            staff.getStaffDetails().getStaffProfessionalDetails().setLatestLoginDate(new Date());
            return staffRepository.save(staff);
        } else {
            throw new UserNotAuthorizedException(ErrorMessage.INVALID_CREDENTIALS, ErrorCodes.INVALID_CREDENTIALS);
        }
    }

    //TODO - password reset/update and set - completed
    //Update an existing password, or reset password for a particular staff
    public String passwordUpdate(UUID staffId, boolean reset, String oldPassword, String newPassword, String emailId){

        AtomicReference<Staff> staffAtomicReference = new AtomicReference<>();
        Optional<Staff> optionalStaff = staffRepository.findById(staffId);
        optionalStaff.ifPresentOrElse( ref->{

            //For password reset
            if(reset){
                if(emailId.equals(ref.getContactDetails().getPersonalEmailId())){
                    LoginCredentials loginCredentials = new LoginCredentials();
//                    loginCredentials.generateCredentials();
                    loginCredentials.generateDefaultCredentials();
                    ref.setLoginCredentials(loginCredentials);
                    ref.setResetPassword(true);

                    List<String> toMail = new ArrayList<>();
                    toMail.add(ref.getContactDetails().getPersonalEmailId());
                    //After password reset, the details are sent to the staff personal mail id
                    serviceInvoker.generateEmail(toMail,null,"Password Updated", "Password reset successful. \n\nNew credentials are:" +
                            "\n\n Username: "+loginCredentials.getUsername()+
                            "\nPassword: "+loginCredentials.getPassword());
                    String encrypted = passwordEncoder.encode(ref.getLoginCredentials().getPassword());
                    ref.getLoginCredentials().setPassword(encrypted);
                    staffAtomicReference.set(staffRepository.save(ref));

                    System.out.println("Reset existing password success");
                }
            }else{

                //For updating existing password
                if(oldPassword.equals(ref.getLoginCredentials().getPassword())){
                    ref.getLoginCredentials().setPassword(newPassword);
                    String encrypted = passwordEncoder.encode(ref.getLoginCredentials().getPassword());
                    ref.getLoginCredentials().setPassword(encrypted);
                    staffAtomicReference.set(staffRepository.save(ref));
                    System.out.println("Update existing password success");
                }else{
                    System.out.println("Update existing password fail");
                    throw new ResourceNotFoundException(ErrorMessage.INVALID_CREDENTIALS, ErrorCodes.INVALID_CREDENTIALS);
                }
            }
        },()->{
            throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
        });
        return "Password updated successfully and sent to personal email.";
    }

    public Staff patchUpdate(UUID id, Staff temp){
        Optional<Staff> ref = staffRepository.findById(id);
        AtomicReference<Staff> staffAtomicReference = new AtomicReference<>();
        ref.ifPresentOrElse( tempRef -> {
            tempRef.updateStaffDetails(temp);
            staffAtomicReference.set(tempRef);
        }, () -> {
            throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
        });
        return staffAtomicReference.get();

    }


    /*
     * Creating an admin from the admin controller and giving respective data access
     */
    public Staff createAdmin(AdminStaff request) {
        try{
            Staff staff = new Staff();
            staff.setContactDetails(request.getDetails());

            // Creating temp objects for adding permissions to the user
            UserPermissions usper = new UserPermissions();
            usper.setUserId(staff.getStaffId());
            ModulePermissions moper = new ModulePermissions();
            List<ModulePermissions> limoper = new ArrayList<>();
            ResourcePermissions reper = new ResourcePermissions();
            List<ResourcePermissions> lireper = new ArrayList<>();

            // Dummy data for KYC, need these fields for dummy staff creation
            KycDetails kyc = new KycDetails();
            kyc.setDocument_type(StaffConstants.KYC_DOCUMENT_TYPE.AADHAAR);
            kyc.setStatus(StaffConstants.VERIFICATION_STATUS.VERIFIED);
            List<KycDetails> kycList = new ArrayList<>();
            kycList.add(kyc);

            StaffDetails staffDetails = new StaffDetails();
            staffDetails.setKycDetails(kycList);

            // For checking whether details are being created in staffDetails repository, with same data
            EmergencyContact econ = new EmergencyContact();
            econ.setRelationshipWithEmployee("Dummy values");
            econ.setContactDetails(request.getDetails());
            staffDetails.setEmergencyContactDetails(econ);
            staff.setStaffDetails(staffDetails);

            // Adding user permissions based on the type of user request
            if (request.getType() == StaffConstants.ADMIN_TYPE.SUPER_ADMIN) {
                usper.setUserId(staff.getStaffId());
                usper.setAdminAccess(true);
                staff.getContactDetails().setName("SUPER_ADMIN");

                rbacService.updateUserPermissions(usper);
                System.out.println("With Great power comes great responsibility, You are a Super Admin now!");

            } else if (request.getType() == StaffConstants.ADMIN_TYPE.MODULE_ADMIN) {
                for (RoleConstants.Modules li1 : request.getModules()) {
                    moper.setModule(li1);
                    moper.setPermissions(null);
                    moper.setModuleAdmin(true);
                    limoper.add(moper);
                }
                usper.setPermissions(limoper);
                staff.getContactDetails().setName("MODULE_ADMIN");
                rbacService.updateUserPermissions(usper);
                System.out.println("Halfway there!, You're a Module admin now");

            } else if (request.getType() == StaffConstants.ADMIN_TYPE.RESOURCE_ADMIN) {
                moper.setPermissions(request.getPermissions());
                limoper.add(moper);
                usper.setPermissions(limoper);

                staff.getContactDetails().setName("RESOURCE_ADMIN");
                rbacService.updateUserPermissions(usper);
                System.out.println("Better luck next time, You're just another Resource admin");
            } else if (request.getType() == StaffConstants.ADMIN_TYPE.NO_ACCESS) {
                staff.getContactDetails().setName("Dummy user - Buck naked, no permissions");
            }
            System.out.println(staff.getStaffId());
            createStaff(staff);
            return staff;

        }catch (Exception e){
            throw new ResourceAlreadyExistException(ErrorMessage.USER_ALREADY_EXIST, ErrorCodes.USER_ALREADY_EXIST);
        }
    }

    public String stripAllPermissions(UUID staffId){

        UserPermissions usper = new UserPermissions();
        usper.setUserId(staffId);
        usper.setPermissions(null);
        usper.setAdminAccess(false);
        UserPermissions temp = rbacService.getUserPermissions(staffId);
        if(temp == null){
            throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
        }
        rbacService.updateUserPermissions(usper);
        return "Try accessing something now, I dare you! You got nothing :P ";
//        return "Deactivated permissions - successful";
    }

    public String updatePermissions(UUID id, UserPermissions perms){
        UserPermissions user = rbacService.getUserPermissions(id);
        perms.setUserId(id);
        if(user != null){
            perms = rbacService.updateUserPermissions(perms);
        }else{
            throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
        }
        return "Way to go! You have the following permissions now: \n"+perms;
    }

    public List<UserPermissions> getFilteredAdminData(UUID id, StaffConstants.ADMIN_TYPE type, String page, String size){

        Sort sort = Sort.by("id");
        Query query;
        Pageable pageable;

        if(page != null && size != null){
            int pageNo = Integer.parseInt(page);
            int siz = Integer.parseInt(size);

            pageable = PageRequest.of(pageNo, siz, sort);
            query = new Query().with(pageable);
        }else{

            query = new Query().with(sort);
        }

        List<Criteria> filterCriteria = new ArrayList<>();

        if(type == StaffConstants.ADMIN_TYPE.SUPER_ADMIN){
            filterCriteria.add(Criteria.where("AdminAccess").is(type));
        }

        if(type == StaffConstants.ADMIN_TYPE.MODULE_ADMIN){
            filterCriteria.add(Criteria.where("Permissions.ModuleAdmin").is(type));
        }

        if(type == StaffConstants.ADMIN_TYPE.RESOURCE_ADMIN){
            filterCriteria.add(Criteria.where("Permissions.ModuleAdmin").is(type));
        }

        if( !filterCriteria.isEmpty() ){
            query = query.addCriteria(new Criteria().orOperator(filterCriteria.toArray(new Criteria[filterCriteria.size()])));
        }

        return mongoTemplate.find(query, UserPermissions.class);
    }
}
