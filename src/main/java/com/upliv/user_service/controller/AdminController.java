package com.upliv.user_service.controller;

import com.upliv.user_service.constants.StaffConstants;
import com.upliv.user_service.model.Response;
import com.upliv.user_service.model.staff.AdminStaff;
import com.upliv.user_service.rbac.models.UserPermissions;
import com.upliv.user_service.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.print.attribute.standard.Media;
import java.util.UUID;

@ApiIgnore
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    StaffService staffService;

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createAdmin(@RequestBody AdminStaff request){
        return new Response(staffService.createAdmin(request));
    }

    @DeleteMapping(path="/remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response removeAdmin(@PathVariable UUID id){
        return new Response(staffService.stripAllPermissions(id));
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updatePermissions(@PathVariable UUID id, @RequestBody UserPermissions perms){
        return new Response(staffService.updatePermissions(id, perms));
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getFilteredAdminData(@RequestParam(required = false) UUID id,
                                         @RequestParam(required = false) StaffConstants.ADMIN_TYPE type,
                                         @RequestParam(required = false) String page, @RequestParam(required = false) String size){
        return new Response(staffService.getFilteredAdminData(id, type, page, size));
    }


}
