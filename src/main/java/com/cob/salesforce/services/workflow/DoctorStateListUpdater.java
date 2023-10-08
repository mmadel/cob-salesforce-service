package com.cob.salesforce.services.workflow;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class DoctorStateListUpdater extends DoctorState {
    ActionEntity createdAction;
    List<TransitionEntity> updatedTransition = new ArrayList<>();

    abstract void createAction();

    abstract void updateTransitions();

    void joinActionToTransitions(String userUUID) {
        updatedTransition.forEach(transitionEntity -> {
            joinActionTransition(createdAction, transitionEntity, userUUID, transitionEntity.getDoctorUUID());
        });
    }

    public void execute(String userUUID) {
        updateTransitions();
        joinActionToTransitions(userUUID);
    }
}
