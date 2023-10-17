package com.cob.salesforce.services.followup;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.entities.FollowupEntity;
import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.followup.FollowupModel;
import com.cob.salesforce.repositories.DoctorRepository;
import com.cob.salesforce.repositories.FollowupRepository;
import com.cob.salesforce.repositories.UserRepository;
import com.cob.salesforce.services.DoctorCacheService;
import com.cob.salesforce.services.administration.UserService;
import com.cob.salesforce.services.transition.impl.FirstTimeTransitionService;
import com.cob.salesforce.services.transition.impl.FollowupTransitionService;
import com.cob.salesforce.services.ui.CountersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    DoctorCacheService doctorCacheService;
    @Autowired
    CountersService countersService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    public Long createFirstFollowup(FollowupModel model) {
        model.getDoctor().setUuid(UUID.randomUUID().toString());
        DoctorEntity createdDoctor = createDoctor(model.getDoctor());
        FollowupEntity toBeCreated = mapper.map(model, FollowupEntity.class);
        toBeCreated.getDoctor().setId(createdDoctor.getId());
        toBeCreated.setClinicId(model.getDoctor().getClinicId());
        firstTimeTransitionService.nextFollowup = model.getNextFollowupDate();

        followupTransitionService.followUpType = model.getFollowUpType();
        firstTimeTransitionService.execute(model.getUser().getUuid(), createdDoctor.getUuid(), model.getDoctor().getClinicId());
        toBeCreated.setActionTransition(firstTimeTransitionService.getCreatedActionTransition());
        toBeCreated.setUser(userRepository.findByUuid(model.getUser().getUuid()).orElseThrow(()-> new IllegalArgumentException("user not found")));
        FollowupEntity created = followupRepository.save(toBeCreated);
        Integer userFirstTimeVisitAchievement = countersService.getUserFirstTimeVisitAchievement(model.getUser().getUuid());
        simpMessagingTemplate.convertAndSend("/topic/first/visit/achieved", model.getUser().getUuid() + "_" +userFirstTimeVisitAchievement);
        return created.getId();
    }

    public Long createFollowup(FollowupModel model) {
        FollowupEntity toBeCreated = mapper.map(model, FollowupEntity.class);
        if(model.getDoctor().getId() == null){
            DoctorEntity doctorEntity = doctorCacheService.getDoctorByUUID(model.getDoctor().getUuid());
            model.getDoctor().setId(doctorEntity.getId());
            toBeCreated.setDoctor(doctorEntity);
        }
        toBeCreated.setClinicId(model.getDoctor().getClinicId());
        followupTransitionService.followUpType = model.getFollowUpType();
        followupTransitionService.execute(model.getUser().getUuid(), model.getDoctor().getUuid(), model.getDoctor().getClinicId());
        toBeCreated.setActionTransition(followupTransitionService.getCreatedActionTransition());
        toBeCreated.setUser(userRepository.findByUuid(model.getUser().getUuid()).orElseThrow(()-> new IllegalArgumentException("user not found")));
        FollowupEntity created = followupRepository.save(toBeCreated);
        return created.getId();
    }

    @CachePut(value = "doctors", key = "#model.uuid")
    public DoctorEntity createDoctor(DoctorModel model) {
        return doctorRepository.save(mapper.map(model, DoctorEntity.class));
    }
}
