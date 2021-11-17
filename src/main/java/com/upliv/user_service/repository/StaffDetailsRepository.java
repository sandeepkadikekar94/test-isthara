package com.upliv.user_service.repository;

import com.upliv.user_service.model.staff.Staff;
import com.upliv.user_service.model.staff.StaffDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StaffDetailsRepository extends MongoRepository<StaffDetails, UUID> {

}

