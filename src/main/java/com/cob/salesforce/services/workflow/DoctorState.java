package com.cob.salesforce.services.workflow;

import com.cob.salesforce.BeanFactory;
import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import com.cob.salesforce.repositories.ActionTransitionRepository;

public abstract class DoctorState {
    void joinActionTransition(ActionEntity action, TransitionEntity transition,
                              String userUUID, String doctorUUID) {
        ActionTransitionRepository actionTransitionRepository = BeanFactory.getBean(ActionTransitionRepository.class);
        ActionTransitionEntity actionTransition = new ActionTransitionEntity();
        actionTransition.setAction(action);
        actionTransition.setTransition(transition);
        actionTransition.setUuidUser(userUUID);
        actionTransition.setUuidDoctor(doctorUUID);
        actionTransitionRepository.save(actionTransition);
    }
}
