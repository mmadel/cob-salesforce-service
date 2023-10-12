package com.cob.salesforce.services.complete;

import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.entities.TransitionEntity;
import com.cob.salesforce.entities.UserEntity;
import com.cob.salesforce.enums.CompletedTaskType;
import com.cob.salesforce.models.complete.CompleteTask;
import com.cob.salesforce.repositories.ActionTransitionRepository;
import com.cob.salesforce.repositories.TransitionRepository;
import com.cob.salesforce.services.DoctorCacheService;
import com.cob.salesforce.services.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompleteTaskService {
    @Autowired
    TransitionRepository transitionRepository;
    @Autowired
    DoctorCacheService doctorCacheService;

    @Autowired
    UserCacheService userCacheService;

    public List<CompleteTask> findCompletedFollowupTask(String clinicId) {
        List<CompleteTask> completeTasks = new ArrayList<>();
        List<TransitionEntity> result = transitionRepository.findCompletedFollowupTask(clinicId);
        result.stream().forEach(transitionEntity -> {
            CompleteTask completeTask = createCompleteTask(CompletedTaskType.Follow_Up
                    , transitionEntity.getDoctorUUID()
                    , transitionEntity.getNextFollowupDate());
            completeTasks.add(completeTask);
        });
        return completeTasks;
    }

    public List<CompleteTask> findCompletedFirstVisitTask(String clinicId) {
        List<CompleteTask> completeTasks = new ArrayList<>();
        List<TransitionEntity> result = transitionRepository.findCompletedFirstVisitTask(clinicId);
        result.stream().forEach(transitionEntity -> {
            CompleteTask completeTask = createCompleteTask(CompletedTaskType.First_Visit
                    , transitionEntity.getDoctorUUID()
                    , transitionEntity.getNextFollowupDate());
            completeTasks.add(completeTask);
        });
        return completeTasks;
    }

    public CompleteTask createCompleteTask(CompletedTaskType completedTaskType,
                                           String doctorUUID,
                                           Long NextFollowupDate) {
        DoctorEntity doctorEntity = doctorCacheService.getDoctorByUUID(doctorUUID);
        CompleteTask completeTask = new CompleteTask();
        completeTask.setDoctorName(doctorEntity.getName());
        completeTask.setDoctorNPI(doctorEntity.getNpi());
        completeTask.setNextFollowupDate(NextFollowupDate);
        completeTask.setCompletedTaskType(completedTaskType);
        return completeTask;
    }
}
