package com.cob.salesforce.services.followup;

import com.cob.salesforce.entities.FollowupEntity;
import com.cob.salesforce.models.followup.FollowupModel;
import com.cob.salesforce.repositories.FollowupRepository;
import com.cob.salesforce.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowupService {

    @Autowired
    FollowupRepository followupRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper mapper;

    public Long createFollowup(FollowupModel model) {
        FollowupEntity created = followupRepository.save(mapper.map(model, FollowupEntity.class));
        return created.getId();
    }
}
