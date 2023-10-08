package com.cob.salesforce.services.transition;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;

public abstract class DoctorTransitionUpdater extends DoctorTransition {
    protected ActionEntity createdAction;
    protected TransitionEntity updatedTransition;

    protected abstract void createAction();

    protected abstract void updateTransition(String doctorUUID);

    public void execute(String userUUID, String doctorUUID) {
        createAction();
        updateTransition(doctorUUID);
        joinActionTransition(createdAction, updatedTransition, userUUID, doctorUUID);
    }
}
