package com.cob.salesforce.services.transition.impl;

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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
            });
            pushNumberOfFollowupDoctors(transitionRepository.saveAll(updatedTransition));
        }
    }

    private void pushNumberOfFollowupDoctors(Iterable<TransitionEntity> transitionsIterable) {
        Set<String> clinicIds = new HashSet<>();
        List<TransitionEntity> transitionEntities =
                StreamSupport.stream(transitionsIterable.spliterator(), false)
                        .collect(Collectors.toList());
        for (TransitionEntity transition : transitionEntities) {
            clinicIds.add(transition.getClinicId());
        }
        clinicIds.forEach(clinicId -> {
            Integer numberOfFollowupDoctors = countersService.getFollowUpDoctors(clinicId);
            simpMessagingTemplate.convertAndSend("/topic/followup", clinicId + "_" + numberOfFollowupDoctors);
        });
    }
}
