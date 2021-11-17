package com.upliv.user_service.utils;

import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.model.common.DbSequence;
import com.upliv.user_service.repository.DbSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DbSequenceHelper {

    public final int ENQUIRY_START_VALUE = 1;

    @Autowired
    DbSequenceRepository sequenceRepository;

    public int getNextSequenceValue(UserConstants.DB_SEQUENCE_TYPE db_sequence_type) {
        AtomicReference<DbSequence> dbSequenceRef = new AtomicReference<>();
        Optional<DbSequence> dbSequenceOptional = sequenceRepository.findById(db_sequence_type);
        dbSequenceOptional.ifPresentOrElse(dbSequence -> {
            dbSequence.setSeqValue(dbSequence.getSeqValue() + 1);
            dbSequenceRef.set(dbSequence);
        }, () -> {
            DbSequence dbSequence = new DbSequence();
            dbSequence.setItemType(db_sequence_type);
            dbSequence.setSeqValue(ENQUIRY_START_VALUE);
            dbSequenceRef.set(dbSequence);
        });
        return sequenceRepository.save(dbSequenceRef.get()).getSeqValue();
    }
}
