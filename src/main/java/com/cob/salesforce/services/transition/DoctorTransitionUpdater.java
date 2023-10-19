package com.cob.salesforce.services.transition;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.entities.TransitionEntity;

public abstract class DoctorTransitionUpdater extends DoctorTransition {
    protected ActionEntity createdAction;
    protected TransitionEntity updatedTransition;



    protected abstract void createAction();

    protected abstract void updateTransition(String doctorUUID,String clinicId,Long nextFollowupDate);

    public void execute(String userUUID, String doctorUUID,String clinicId , Long nextFollowupDate) {
        createAction();
        updateTransition(doctorUUID,clinicId,nextFollowupDate);
        joinActionTransition(createdAction, updatedTransition, userUUID, doctorUUID);
    }

    public TransitionEntity getUpdatedTransition(){
        return updatedTransition;
    }

    public ActionTransitionEntity getCreatedActionTransition(){
        return createdActionTransition;
    }
}
