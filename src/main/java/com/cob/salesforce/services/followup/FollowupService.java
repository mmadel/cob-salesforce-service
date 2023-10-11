package com.cob.salesforce.services.followup;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.entities.FollowupEntity;
import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.followup.FollowupModel;
import com.cob.salesforce.repositories.DoctorRepository;
import com.cob.salesforce.repositories.FollowupRepository;
import com.cob.salesforce.repositories.UserRepository;
import com.cob.salesforce.services.transition.impl.FirstTimeTransitionService;
import com.cob.salesforce.services.transition.impl.FollowupTransitionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
    @Autowired
    DoctorRepository doctorRepository;

    public Long createFirstFollowup(FollowupModel model) {
        DoctorEntity createdDoctor = createDoctor(model.getDoctor());
        FollowupEntity toBeCreated = mapper.map(model, FollowupEntity.class);
        toBeCreated.getDoctor().setId(createdDoctor.getId());
        toBeCreated.setClinicId(model.getDoctor().getClinicId());
        FollowupEntity created = followupRepository.save(toBeCreated);
        firstTimeTransitionService.nextFollowup = model.getNextFollowupDate();
        firstTimeTransitionService.execute(model.getUser().getUuid(), createdDoctor.getUuid(), model.getDoctor().getClinicId());
        return created.getId();
    }

    public Long createFollowup(FollowupModel model) {
        FollowupEntity toBeCreated = mapper.map(model, FollowupEntity.class);
        toBeCreated.setClinicId(model.getDoctor().getClinicId());
        FollowupEntity created = followupRepository.save(toBeCreated);
        followupTransitionService.execute(model.getUser().getUuid(), model.getDoctor().getUuid(), model.getDoctor().getClinicId());
        return created.getId();
    }

    private DoctorEntity createDoctor(DoctorModel model) {
        model.setUuid(UUID.randomUUID().toString());
        return doctorRepository.save(mapper.map(model, DoctorEntity.class));
    }
}
