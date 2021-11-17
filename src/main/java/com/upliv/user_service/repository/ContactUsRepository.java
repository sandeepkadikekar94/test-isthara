package com.upliv.user_service.repository;

import com.upliv.user_service.model.ContactUs;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ContactUsRepository extends MongoRepository<ContactUs, UUID> {
}
