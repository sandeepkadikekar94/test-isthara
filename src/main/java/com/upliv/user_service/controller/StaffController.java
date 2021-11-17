package com.upliv.user_service.controller;

import com.upliv.user_service.constants.StaffConstants;
import com.upliv.user_service.model.Response;
import com.upliv.user_service.model.staff.*;
import com.upliv.user_service.model.staff.attendance.DailyAttendance;
import com.upliv.user_service.rbac.models.RoleConstants;
import com.upliv.user_service.repository.StaffRepository;
import com.upliv.user_service.service.StaffService;
import com.upliv.user_service.utils.RbacAccessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Month;
import java.util.UUID;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private RbacAccessUtil rbacAccessUtil;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createStaff(@RequestBody Staff staff, HttpServletRequest request, HttpServletResponse response) {
        //Admin, Staff create access
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.S_CREATE, null);
        return new Response(staffService.createStaff(staff));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteStaff(@PathVariable UUID id, HttpServletRequest request, HttpServletResponse response){
        //Admin, Staff delete access
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.S_DELETE, id);
        return new Response(staffService.deleteStaff(id));
    }


    //Advanced : All the details
    //API to update all the staff details, need admin access for this API
    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateStaff(@RequestBody UpdateStaff updateStaff, HttpServletRequest request, HttpServletResponse response) {
        //Admin, Staff edit access
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.S_EDIT_ALL, updateStaff.getStaff().getStaffId());
        return new Response(staffService.updateStaff(updateStaff));
    }

    //Basic : Name, mobile, email, image, address, EmergencyContact, update kyc not delete
    // API to update the basic staff details, need basic edit access for this
    @PutMapping(path = "{id}/update/basic", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateStaffBasic(@PathVariable UUID id,
                                     @RequestBody UpdateStaffBasic details,
                                     @RequestParam(required = false) boolean contact,
                                     @RequestParam(required = false) boolean emergencyContact,
                                     @RequestParam(required = false) boolean kyc,
                                     HttpServletRequest request, HttpServletResponse response){
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.S_EDIT_BASIC, id);
        return new Response(staffService.updateStaffBasic(id, details,contact, emergencyContact, kyc));
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getStaff(@RequestParam(required = false) UUID staffId,
                             @RequestParam(required = false, defaultValue = "true") String allDetails,
                             HttpServletRequest request, HttpServletResponse response) {
        //Admin, Staff view access
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.S_VIEW, null);
        return new Response(staffService.getStaff(staffId,Boolean.parseBoolean(allDetails)));
    }

    //To generate new login credentials for a particular staff, the new credentials will be sent to the personal and work email id of staff
    @GetMapping(path = "{id}/credentials/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response generateNewLoginCredentials(@PathVariable UUID id, HttpServletRequest request, HttpServletResponse response) {
        //Admin, Staff create access
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.S_CREATE, id);
        return new Response(staffService.generateCredentials(id));
    }

    //To get staff details based on the filters applied
    @GetMapping(path = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getFilteredStaff(@RequestParam(required = false) UUID staffId,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(required = false) String locality,
                                     @RequestParam(required = false) String propertyName,
                                     @RequestParam(required = false) String department,
                                     @RequestParam(required = false) String employmentType,
                                     @RequestParam(required = false) Boolean status,
                                     @RequestParam(required = false, defaultValue = "true") String allDetails,
                                     @RequestParam(required = false) String pageNo, @RequestParam(required = false) String size,
                                     HttpServletRequest request, HttpServletResponse response){
        //Admin, Staff view access
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.S_VIEW, null);
        return new Response(staffService.getFilteredStaff(staffId, city, locality, propertyName, department, employmentType, status, allDetails, pageNo, size));
    }

    //Send sms or email notification to the staff
    @PostMapping(path = "/{id}/notify", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response notifyStaff(@PathVariable UUID id,
                                @RequestParam(required = false) boolean smsNotification,
                                @RequestParam(required = false) boolean emailNotification,
                                @RequestParam(required = false) String emailId,
//                                @RequestParam(required = false) ContactDetails staffDetails,
                                @RequestBody(required = false) StaffNotification request){
//        return new Response( staffService.notifyStaff(id, smsNotification, emailNotification, request, staffDetails));
        return new Response( staffService.notifyStaff(id, smsNotification, emailNotification, request, emailId));
    }

    // Add attendance to one staff
    @PostMapping(path = "{id}/attendance/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addAttendanceToStaff(@PathVariable UUID id,@RequestBody DailyAttendance request){
        return new Response(staffService.addDailyAttendance(id, request));
    }

    // Get attendance details based on applied filters
    @GetMapping(path = "/attendance/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getFilteredStaffAttendance(@RequestParam(required = false) UUID id,
                                               @RequestParam(required = false) int pageNo,
                                               @RequestParam(required = false) int size,
                                               @RequestParam(required = false) StaffConstants.MONTHS_FOR_ATTENDANCE month,
                                               @RequestParam(required = false) StaffConstants.EMPLOYMENT_STATUS status){
        return new Response(staffService.filteredAttendance(id, pageNo, size, month, status));
    }

    // Returns the percentage of attendance per month, input arguments - 2
    // Param - 1 : Month for which percentage is to be calculated
    // Param - 2 : Maximum number of working days in that month
    @GetMapping(path="{id}/attendance/months", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAttendancePercentage(@PathVariable UUID id,
                                            @RequestParam(required = true) Month month,
                                            @RequestParam(required = true) float maxWorkingDays){
        return new Response(staffService.getAttendancePercentage(id, month, maxWorkingDays));
    }

    @PostMapping(path = "/ist/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response staffLogin(@RequestBody StaffLoginRequest request){
        return new Response(staffService.login(request));
    }

    // Reset or update password API
    //Personal email id mandatory field for password reset, to confirm the staff details
    @PostMapping(path = "/{id}/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updatePassword(@PathVariable UUID id,
                                   @RequestParam(required = false) boolean reset,
                                   @RequestParam(required = false) String oldPassword,
                                   @RequestParam(required = false) String newPassword,
                                   @RequestParam(required = false) String emailId,
                                   HttpServletRequest request, HttpServletResponse response){
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.S_EDIT_BASIC, id);
        return new Response(staffService.passwordUpdate(id, reset,oldPassword,newPassword,emailId));
    }

    //
//    @PatchMapping(path = "{id}/patch", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response patchTrial(@PathVariable UUID id,
//                               @RequestBody Staff updates){
//        return new Response(staffRepository.save(updates, id);
//        return new Response(staffService.patchUpdate(id, updates));
//    }

}
