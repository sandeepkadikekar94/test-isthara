package com.upliv.user_service.rbac.controller;

import com.upliv.user_service.model.Response;
import com.upliv.user_service.model.vendor.Vendor;
import com.upliv.user_service.rbac.models.Module;
import com.upliv.user_service.rbac.models.Role;
import com.upliv.user_service.rbac.models.UserPermissions;
import com.upliv.user_service.rbac.service.RbacService;
import com.upliv.user_service.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/rbac")
public class RbacController {

    @Autowired
    private RbacService rbacService;

    @PostMapping(path = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createRole(@Valid @RequestBody Role role) {
        return new Response(rbacService.createRole(role));
    }

    @GetMapping(path = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAllRoles() {
        return new Response(rbacService.getRoles());
    }

    @PutMapping(path = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateModule(@Valid @RequestBody Role role) {
        return new Response(rbacService.updateRole(role));
    }

    @PostMapping(path = "/role/{roleId}/assign/{userId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response assignrole(@PathVariable UUID roleId, @PathVariable UUID userId){
        return new Response(rbacService.assignrole(roleId, userId));
    }
    //Permissions
    @PutMapping(path = "/permission", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateUserPermissions(@Valid @RequestBody UserPermissions userPermissions) {
        return new Response(rbacService.updateUserPermissions(userPermissions));
    }

    @GetMapping(path = "/permission/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getUserPermissions(@PathVariable UUID id) {
        return new Response(rbacService.getUserPermissions(id));
    }
}
