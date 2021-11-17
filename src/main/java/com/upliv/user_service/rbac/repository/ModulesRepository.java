package com.upliv.user_service.rbac.repository;

import com.upliv.user_service.rbac.models.Module;
import com.upliv.user_service.rbac.models.RoleGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ModulesRepository extends MongoRepository<Module, UUID> {
}
