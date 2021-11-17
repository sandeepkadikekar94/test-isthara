package com.upliv.user_service.repository;

import com.upliv.user_service.model.OTPData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OTPRepository extends MongoRepository<OTPData, UUID> {
}
