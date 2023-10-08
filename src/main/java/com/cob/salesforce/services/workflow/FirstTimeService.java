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
public class FirstTimeService extends DoctorStateCreator {
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    TransitionRepository transitionRepository;
    @Autowired
    ActionTransitionRepository actionTransitionRepository;
    @Override
    void createAction() {
        ActionEntity entity = new ActionEntity();
        entity.setActionType(ActionType.TOUCH);
        createdAction = actionRepository.save(entity);
    }
    @Override
    void createTransition() {
        TransitionEntity entity = new TransitionEntity();
        entity.setState(State.INITIALIZE);
        createdTransition = transitionRepository.save(entity);
    }
}
