package com.upliv.user_service.rbac.repository;

import com.upliv.user_service.rbac.models.Role;
import com.upliv.user_service.rbac.models.RoleGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RoleGroupRepository extends MongoRepository<RoleGroup, UUID> {
}
