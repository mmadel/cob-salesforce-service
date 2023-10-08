package com.cob.salesforce.services.transition;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;

public abstract class DoctorTransitionCreator extends DoctorTransition {
    protected ActionEntity createdAction;
    protected TransitionEntity createdTransition;

    protected abstract void createAction();

    protected abstract void createTransition(String doctorUUID);

    public void execute(String userUUID, String doctorUUID) {
        createAction();
        createTransition(doctorUUID);
        joinActionTransition(createdAction, createdTransition, userUUID, doctorUUID);
    }
}
