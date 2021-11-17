package com.upliv.user_service.rbac.repository;

import com.upliv.user_service.rbac.models.Module;
import com.upliv.user_service.rbac.models.UserPermissions;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserPermissionsRepository extends MongoRepository<UserPermissions, UUID> {
}
