package com.cob.salesforce.services.transition;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class DoctorTransitionListUpdater extends DoctorTransition {
    protected ActionEntity createdAction;
    protected List<TransitionEntity> updatedTransition = new ArrayList<>();

    protected abstract void createAction();

    protected abstract void updateTransitions();

    void joinActionToTransitions(String userUUID) {
        updatedTransition.forEach(transitionEntity -> joinActionTransition(createdAction, transitionEntity, userUUID, transitionEntity.getDoctorUUID()));
    }

    public void execute(String userUUID) {
        updateTransitions();
        joinActionToTransitions(userUUID);
    }
}
