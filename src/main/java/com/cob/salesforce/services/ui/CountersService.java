package com.cob.salesforce.services.ui;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.entities.UserTargetEntity;
import com.cob.salesforce.models.intake.TargetDoctor;
import com.cob.salesforce.repositories.ActionTransitionRepository;
import com.cob.salesforce.repositories.DoctorRepository;
import com.cob.salesforce.repositories.TransitionRepository;
import com.cob.salesforce.repositories.UserTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountersService {
    @Autowired
    TransitionRepository transitionRepository;

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ActionTransitionRepository actionTransitionRepository;
    @Autowired
    UserTargetRepository userTargetRepository;

    public Integer getPotentialsDoctors(String clinicId) {
        return transitionRepository.findPotentialDoctorsCounter(clinicId);
    }

    public Integer getFollowUpDoctors(String clinicId) {
        return transitionRepository.findFollowupDoctorsCounter(clinicId);
    }

    public Integer getUserAchievement(String userUUID) {
        return userTargetRepository.findUserByUUID(userUUID).getAchievement();
    }
    public Integer getUserFirstTimeVisitTarget(String userUUID){
        return userTargetRepository.findUserByUUID(userUUID).getFirstTime();
    }
}
