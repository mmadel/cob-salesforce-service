package com.cob.salesforce.services.workflow;

import com.cob.salesforce.BeanFactory;
import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import com.cob.salesforce.repositories.ActionTransitionRepository;

public abstract class DoctorStateCreator {
    ActionEntity createdAction;
    TransitionEntity createdTransition;

    abstract void createAction();

    abstract void createTransition();

     void joinActionTransition(String userUUID , String doctorUUID){
         ActionTransitionRepository actionTransitionRepository = BeanFactory.getBean(ActionTransitionRepository.class);
         ActionTransitionEntity actionTransition = new ActionTransitionEntity();
         actionTransition.setAction(createdAction);
         actionTransition.setTransition(createdTransition);
         actionTransition.setUuidUser(userUUID);
         actionTransition.setUuidDoctor(doctorUUID);
         actionTransitionRepository.save(actionTransition);
     }

    public void execute(String userUUID , String doctorUUID) {
        createAction();
        createTransition();
        joinActionTransition(userUUID,doctorUUID);
    }
}
