package com.cob.salesforce.services.ui;

import com.cob.salesforce.repositories.TransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountersService {
    @Autowired
    TransitionRepository transitionRepository;
    public Integer getPotentialsDoctors(String clinicId) {
        return transitionRepository.findPotentialDoctorsCounter(clinicId);
    }

    public Integer getFollowUpDoctors(String clinicId) {
        return transitionRepository.findFollowupDoctorsCounter(clinicId);
    }
}
