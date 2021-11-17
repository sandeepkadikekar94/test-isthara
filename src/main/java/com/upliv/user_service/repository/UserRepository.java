package com.upliv.user_service.repository;

import com.upliv.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    @Query("{'email': '?0'}")
    User findUserByEmail(String email);

    @Query("{'countryCode': '?0', 'mobile':?1}")
    User findUserByMobile(String countryCode, String mobile);


    @Query("{'email':'?0'}")
    List<User> findUsers(String emails);
}
