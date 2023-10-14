package com.cob.salesforce.services.transition.impl;

import com.cob.salesforce.BeanFactory;
import com.cob.salesforce.entities.ActionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import com.cob.salesforce.enums.ActionType;
import com.cob.salesforce.enums.State;
import com.cob.salesforce.repositories.ActionRepository;
import com.cob.salesforce.repositories.ActionTransitionRepository;
import com.cob.salesforce.repositories.TransitionRepository;
import com.cob.salesforce.services.transition.DoctorTransitionListUpdater;
import com.cob.salesforce.services.ui.CountersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class SchedulerTransitionService extends DoctorTransitionListUpdater {
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    TransitionRepository transitionRepository;
    @Autowired
    ActionTransitionRepository actionTransitionRepository;

    @Autowired
    CountersService countersService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    protected void createAction() {
        ActionEntity entity = new ActionEntity();
        entity.setActionType(ActionType.TRIGGER_FOLLOW_UP);
        createdAction = actionRepository.save(entity);
    }

    @Override
    protected void updateTransitions() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        List<TransitionEntity> transitions = transitionRepository.findFollowUpDoctors(calendar.getTimeInMillis());
        if (transitions.size() > 0) {
            createAction();
            transitions.forEach(transitionEntity -> {
                transitionEntity.setNextFollowupDate(null);
                transitionEntity.setState(State.FOLLOWUP);
                updatedTransition.add(transitionEntity);
                Integer numberOfFollowupDoctors = countersService.getFollowUpDoctors(transitionEntity.getClinicId());

                simpMessagingTemplate.convertAndSend("/topic/followup", transitionEntity.getClinicId() + "_" + numberOfFollowupDoctors);
            });
            transitionRepository.saveAll(updatedTransition);
        }
    }

}
