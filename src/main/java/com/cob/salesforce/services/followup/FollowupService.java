package com.cob.salesforce.services.followup;

import com.cob.salesforce.entities.FollowupEntity;
import com.cob.salesforce.models.followup.FollowupModel;
import com.cob.salesforce.repositories.FollowupRepository;
import com.cob.salesforce.repositories.UserRepository;
import com.cob.salesforce.services.transition.impl.FirstTimeTransitionService;
import com.cob.salesforce.services.transition.impl.FollowupTransitionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FollowupService {

    @Autowired
    FollowupRepository followupRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    FollowupTransitionService followupTransitionService;
    @Autowired
    FirstTimeTransitionService firstTimeTransitionService;

    public Long createFirstFollowup(FollowupModel model) {
        FollowupEntity created = followupRepository.save(mapper.map(model, FollowupEntity.class));
        firstTimeTransitionService.execute(model.getUser().getUuid(), model.getDoctor().getUuid(), model.getDoctor().getClinicId());
        return created.getId();
    }

    public Long createFollowup(FollowupModel model) {
        FollowupEntity created = followupRepository.save(mapper.map(model, FollowupEntity.class));
        followupTransitionService.execute(model.getUser().getUuid(), model.getDoctor().getUuid(), model.getDoctor().getClinicId());
        return created.getId();
    }
}
