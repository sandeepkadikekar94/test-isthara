package com.upliv.user_service.repository;

import com.upliv.user_service.model.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserDetailsRepository extends MongoRepository<UserDetails, UUID> {
}
