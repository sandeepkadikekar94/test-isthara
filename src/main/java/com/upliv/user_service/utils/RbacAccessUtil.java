package com.upliv.user_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upliv.user_service.exceptions.ErrorCodes;
import com.upliv.user_service.exceptions.ErrorMessage;
import com.upliv.user_service.exceptions.UserNotAuthorizedException;
import com.upliv.user_service.rbac.models.ModulePermissions;
import com.upliv.user_service.rbac.models.ResourcePermissions;
import com.upliv.user_service.rbac.models.RoleConstants;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class RbacAccessUtil {

    @Value("${feign.secretKey}")
    private String feignSecretKey;

    public void hasAccess(HttpServletRequest request, HttpServletResponse response,
                          RoleConstants.Permission permission, UUID resourceId) {
        try {
            String secretKey = request.getHeader("X-Auth-Token");
            if(!TextUtils.isBlank(secretKey) && secretKey.equals(feignSecretKey)) {
                return;
            }
            ModulePermissions modulePermissions = getModulePermissions(request);
            if(modulePermissions.isModuleAdmin()) {
                return;
            }
            boolean hasAccess = false;
            for(ResourcePermissions resourcePermissions : modulePermissions.getPermissions()) {
                if(resourcePermissions.getPermission() == permission) {
                    if(resourceId == null) {
                        hasAccess = true;
                    } else if(resourcePermissions.isAllResources()
                            || resourcePermissions.getResourceIds().contains(resourceId)) {
                        hasAccess = true;
                    }
                }
            }
            if(!hasAccess) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
                throw new UserNotAuthorizedException(ErrorMessage.NOT_AUTHORIZED, ErrorCodes.NOT_AUTHORIZED);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private ModulePermissions getModulePermissions(HttpServletRequest request) {
        String permissions = request.getHeader("permissions");
        ObjectMapper mapper = new ObjectMapper();
        ModulePermissions modulePermissions = null;
        try {
            modulePermissions = mapper.readValue(permissions, ModulePermissions.class);
            System.out.println(modulePermissions.toString());
            return modulePermissions;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
