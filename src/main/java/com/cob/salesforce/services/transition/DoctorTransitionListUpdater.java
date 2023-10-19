package com.cob.salesforce.services.transition;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class DoctorTransitionListUpdater extends DoctorTransition {
    protected ActionEntity createdAction;
    protected List<TransitionEntity> updatedTransitions;

    protected abstract void createAction();

    protected abstract void updateTransitions();

    void joinActionToTransitions(String userUUID) {
        for (TransitionEntity transitionEntity : updatedTransitions) {
            joinActionTransition(createdAction, transitionEntity, userUUID, transitionEntity.getDoctorUUID());
        }
    }

    public void execute(String userUUID) {
        updatedTransitions = new ArrayList<>();
        updateTransitions();
        joinActionToTransitions(userUUID);
    }
}
