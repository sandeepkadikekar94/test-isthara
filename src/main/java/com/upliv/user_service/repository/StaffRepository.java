package com.upliv.user_service.repository;

import com.upliv.user_service.model.common.Mobile;
import com.upliv.user_service.model.staff.Staff;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends MongoRepository<Staff, UUID> {

    //Retrieve active staff, with pagination
    @Query("{'isActive':true}")
    List<Staff> findActiveStaffDetails(Pageable pageable);

    //Retrieve active staff, without pagination
    @Query("{'isActive':true}")
    List<Staff> findActiveStaffDetails();

    //Retrieve active staff, with pagination
    @Query(value="{'isActive':true}", fields="{ staffDetails : 0 }")
    List<Staff> findActiveStaff(Pageable pageable);

    //Retrieve active staff, without pagination
    @Query(value="{'isActive':true}", fields="{ staffDetails : 0 }")
    List<Staff> findActiveStaff();

    @Query("{'contactDetails.mobile.mobile': ?0}")
    Staff getStaffByMobile(String mobile);

    @Query("{'loginCredentials.username': ?0}")
    Staff getStaffByUserName(String userName);

    @Query("{'contactDetails.mobile.mobile': ?0}")
    Staff findByMobile(String mobile);
}

