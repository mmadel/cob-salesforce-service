package com.cob.salesforce.services.workflow;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;

public abstract class DoctorStateUpdater extends DoctorState {
    ActionEntity createdAction;
    TransitionEntity updatedTransition;

    abstract void createAction();

    abstract void updateTransition(String doctorUUID);

    public void execute(String userUUID, String doctorUUID) {
        createAction();
        updateTransition(doctorUUID);
        joinActionTransition(createdAction, updatedTransition, userUUID, doctorUUID);
    }
}
