package com.upliv.user_service.controller;

import com.upliv.user_service.model.*;
import com.upliv.user_service.rbac.models.RoleConstants;
import com.upliv.user_service.service.UserDetailsService;
import com.upliv.user_service.utils.RbacAccessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/userdetails")
public class UserDetailsController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RbacAccessUtil rbacAccessUtil;

    @PostMapping(path = "/ist/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response signUp(@Valid  @RequestBody User user) {
        return new Response(userDetailsService.signup(user));
    }

    @PostMapping(path = "/ist/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response login(@RequestBody LoginRequest loginRequest) {
        return new Response(userDetailsService.login(loginRequest));
    }

    @PostMapping(path = "/ist/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response verifyOtp(@RequestBody ValidateOtpRequest otpRequest) {
        return new Response(userDetailsService.verifyOtp(otpRequest));
    }

    @PostMapping(path = "/ist/resendotp", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response resendOtp(@RequestBody LoginRequest loginRequest) {
        return new Response(userDetailsService.resendOtp(loginRequest));
    }

    @PostMapping(path = "/updateUserDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateUserDetails(@Valid  @RequestBody UserRequest userRequest, HttpServletRequest request, HttpServletResponse response) {
        //Actual User, User edit permission
        String userId = request.getHeader("user_id");
        if(!UUID.fromString(userId).equals(userRequest.getUser().getUserId())) {
            rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.U_EDIT_ALL, null);
        }
        return new Response(userDetailsService.updateUserDetails(userRequest,userId));
    }
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getUserDetailsById(@PathVariable UUID id, HttpServletRequest request, HttpServletResponse response) {
        //Actual User, User list permission
        String userId = request.getHeader("user_id");
        if(userId == null  || !UUID.fromString(userId).equals(id)) {
            rbacAccessUtil.hasAccess(request,response, RoleConstants.Permission.U_VIEW, id);
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setUser(userDetailsService.getUserById(id));
        userRequest.setUserDetails(userDetailsService.getUserDetailsById(id,false));
        return new Response(userRequest);
    }

    @PostMapping(path = "/ist/contactus", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response contactus(@Valid  @RequestBody ContactUs contactUs) {
        return new Response(userDetailsService.createContactUs(contactUs));
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAllContactUs(HttpServletRequest request, HttpServletResponse response) {
        //Admin
        rbacAccessUtil.hasAccess(request, response, RoleConstants.Permission.U_VIEW, null);
        return new Response(userDetailsService.getContactUs());
    }

}
