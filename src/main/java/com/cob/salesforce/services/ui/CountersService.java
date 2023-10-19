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
        UserTargetEntity userTargetEntity = userTargetRepository.findUserByUUID(userUUID);
        return userTargetEntity != null ? userTargetEntity.getAchievement() : 0;
    }

    public Integer getUserFirstTimeVisitTarget(String userUUID) {
        UserTargetEntity userTargetEntity = userTargetRepository.findUserByUUID(userUUID);
        return userTargetEntity != null ? userTargetEntity.getFirstTime() : 0;
    }

    public Integer getUserFirstTimeVisitAchievement(String userUUID) {
        return actionTransitionRepository.getFirstTimeUserTargetAchieved(userUUID);
    }

}
