package com.upliv.user_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.exceptions.ErrorCodes;
import com.upliv.user_service.exceptions.ErrorMessage;
import com.upliv.user_service.exceptions.UserNotAuthorizedException;
import com.upliv.user_service.model.*;
import com.upliv.user_service.rbac.models.ModulePermissions;
import com.upliv.user_service.rbac.models.ResourcePermissions;
import com.upliv.user_service.rbac.models.RoleConstants;
import com.upliv.user_service.service.UserService;
import com.upliv.user_service.utils.RbacAccessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RbacAccessUtil rbacAccessUtil;

    @PostMapping(path = "/ist/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response signup(@Valid  @RequestBody User user) {
        return new Response(userService.signup(user));
    }


    @PostMapping(path = "/ist/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response login(@RequestBody LoginRequest loginRequest) {
        return new Response(userService.login(loginRequest));
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        //Admin access, User list permission
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.U_VIEW, null);
        return new Response(userService.getUsers());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getUserById(@PathVariable UUID id, HttpServletRequest request, HttpServletResponse response) {
        //Actual User, User list permission
        String userId = request.getHeader("user_id");
        if(userId == null  || !UUID.fromString(userId).equals(id)) {
            rbacAccessUtil.hasAccess(request,response, RoleConstants.Permission.U_VIEW, id);
        }
        return new Response(userService.getUserById(id));
    }

    @PostMapping(path = "/ist/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response verifyOtp(@RequestBody ValidateOtpRequest otpRequest) {
        return new Response(userService.verifyOtp(otpRequest));
    }

    @PostMapping(path = "/ist/resendotp", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response resendOtp(@RequestBody LoginRequest loginRequest) {
        return new Response(userService.resendOtp(loginRequest));
    }

    @PutMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateUser(@RequestBody User user , HttpServletRequest request, HttpServletResponse response){
        //Actual User, User edit permission
        String userId = request.getHeader("user_id");
        if(!UUID.fromString(userId).equals(user.getUserId())) {
            rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.U_EDIT_ALL, null);
        }
        return new Response(userService.updateUser(userId, user));
    }

    @PutMapping(path = "/{userId}/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response logout(@PathVariable UUID userId, @RequestBody DeviceInfo deviceInfo,
                           HttpServletRequest request, HttpServletResponse response){
        //Actual User
        String user_id = request.getHeader("user_id");
        if(!UUID.fromString(user_id).equals(userId)) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
            throw new UserNotAuthorizedException(ErrorMessage.NOT_AUTHORIZED, ErrorCodes.NOT_AUTHORIZED);
        }
        userService.logout(userId, deviceInfo);
        return new Response("logout success!");
    }

    @PostMapping(path = "/internal/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createNewUserInternal(@RequestBody UserInviteRequest userInviteRequest, HttpServletRequest request, HttpServletResponse response) {
        // Enquiry create permission
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.U_CREATE, null);
        return new Response(userService.createNewUserInternal(userInviteRequest));
    }

    @PostMapping("/ist/upload")
    public List<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws Exception{
       return userService.upload(file);
    }

    @PostMapping(path = "/ist/verify/checkin")
    public Response checkUserForCheckinRequest(@Valid @RequestBody OldErpUserRequest req){
        System.out.println("Method : checkin");
        req.setRequestType(UserConstants.REQUEST_TYPE.CHECK_IN);
        return new Response(userService.requestFromOldErp(req));
    }

    @PostMapping(path = "/ist/verify/checkout")
    public Response checkUserForCheckoutRequest(@Valid @RequestBody OldErpUserRequest req){
        System.out.println("Method : checkout");
        req.setRequestType(UserConstants.REQUEST_TYPE.CHECK_OUT);
        return new Response(userService.requestFromOldErp(req));
    }
}
