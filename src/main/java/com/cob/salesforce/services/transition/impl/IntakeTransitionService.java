package com.cob.salesforce.services.transition.impl;

import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import com.cob.salesforce.enums.ActionType;
import com.cob.salesforce.enums.State;
import com.cob.salesforce.repositories.ActionRepository;
import com.cob.salesforce.repositories.TransitionRepository;
import com.cob.salesforce.services.transition.DoctorTransitionCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntakeTransitionService extends DoctorTransitionCreator {
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    TransitionRepository transitionRepository;

    @Override
    protected void createAction() {
        ActionEntity entity = new ActionEntity();
        entity.setActionType(ActionType.PUSH);
        createdAction = actionRepository.save(entity);
    }

    @Override
    protected void createTransition(String doctorUUID,String clinicId) {
        TransitionEntity entity = new TransitionEntity();
        entity.setState(State.POTENTIAL);
        entity.setDoctorUUID(doctorUUID);
        entity.setClinicId(clinicId);
        createdTransition = transitionRepository.save(entity);
    }
}
