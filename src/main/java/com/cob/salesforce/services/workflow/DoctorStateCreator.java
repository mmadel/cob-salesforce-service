package com.cob.salesforce.services.workflow;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;

public abstract class DoctorStateCreator extends DoctorState {
    ActionEntity createdAction;
    TransitionEntity createdTransition;

    abstract void createAction();

    abstract void createTransition(String doctorUUID);

    public void execute(String userUUID, String doctorUUID) {
        createAction();
        createTransition(doctorUUID);
        joinActionTransition(createdAction, createdTransition, userUUID, doctorUUID);
    }
}
