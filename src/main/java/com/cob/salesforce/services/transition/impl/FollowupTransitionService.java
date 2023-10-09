package com.cob.salesforce.services.transition.impl;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import com.cob.salesforce.enums.ActionType;
import com.cob.salesforce.enums.State;
import com.cob.salesforce.repositories.ActionRepository;
import com.cob.salesforce.repositories.ActionTransitionRepository;
import com.cob.salesforce.repositories.TransitionRepository;
import com.cob.salesforce.services.transition.DoctorTransitionUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowupTransitionService extends DoctorTransitionUpdater {
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    ActionTransitionRepository actionTransitionRepository;
    @Autowired
    TransitionRepository transitionRepository;

    @Override
    protected void createAction() {
        ActionEntity entity = new ActionEntity();
        entity.setActionType(ActionType.VISIT_FOLLOW_UP);
        createdAction = actionRepository.save(entity);
    }

    @Override
    protected void updateTransition(String doctorUUID,String clinicId) {
        TransitionEntity potentialTransitionByDoctor = actionTransitionRepository.findPotentialTransitionByDoctor(doctorUUID,clinicId);
        potentialTransitionByDoctor.setState(State.COMPLETE);
        potentialTransitionByDoctor.setNextFollowupDate(1698789600000L);
        updatedTransition = transitionRepository.save(potentialTransitionByDoctor);

    }
}
