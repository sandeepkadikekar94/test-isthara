package com.upliv.user_service.rbac.repository;

import com.upliv.user_service.model.ContactUs;
import com.upliv.user_service.rbac.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RoleRepository extends MongoRepository<Role, UUID> {
}
