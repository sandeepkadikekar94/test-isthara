package com.upliv.user_service.repository;

import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.model.common.DbSequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DbSequenceRepository extends MongoRepository<DbSequence, UserConstants.DB_SEQUENCE_TYPE> {

}
