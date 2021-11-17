package com.upliv.user_service.repository;

import com.upliv.user_service.model.AppVersions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppVersionsRepository extends MongoRepository<AppVersions, String> {
}
