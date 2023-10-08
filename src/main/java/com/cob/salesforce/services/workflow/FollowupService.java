package com.cob.salesforce.services.workflow;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import com.cob.salesforce.enums.ActionType;
import com.cob.salesforce.enums.State;
import com.cob.salesforce.repositories.ActionRepository;
import com.cob.salesforce.repositories.ActionTransitionRepository;
import com.cob.salesforce.repositories.TransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowupService extends DoctorStateUpdater {
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    ActionTransitionRepository actionTransitionRepository;
    @Autowired
    TransitionRepository transitionRepository;

    @Override
    void createAction() {
        ActionEntity entity = new ActionEntity();
        entity.setActionType(ActionType.VISIT_FOLLOW_UP);
        createdAction = actionRepository.save(entity);
    }

    @Override
    void updateTransition(String doctorUUID) {
        TransitionEntity potentialTransitionByDoctor = actionTransitionRepository.findPotentialTransitionByDoctor(doctorUUID);
        potentialTransitionByDoctor.setState(State.COMPLETE);
        potentialTransitionByDoctor.setNextFollowupDate(1698789600000L);
        updatedTransition = transitionRepository.save(potentialTransitionByDoctor);

    }
}
