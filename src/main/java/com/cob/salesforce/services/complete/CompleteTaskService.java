package com.cob.salesforce.services.complete;

import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.enums.CompletedTaskType;
import com.cob.salesforce.models.complete.CompleteTask;
import com.cob.salesforce.repositories.ActionTransitionRepository;
import com.cob.salesforce.services.DoctorCacheService;
import com.cob.salesforce.services.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompleteTaskService {
    @Autowired
    ActionTransitionRepository actionTransitionRepository;
    @Autowired
    DoctorCacheService doctorCacheService;

    @Autowired
    UserCacheService userCacheService;

    public List<CompleteTask> findCompletedFollowupTask(String clinicId) {
        List<CompleteTask> completeTasks = new ArrayList<>();
        List<ActionTransitionEntity> result = actionTransitionRepository.findCompletedFollowupTask(clinicId);
        result.stream().forEach(actionTransitionEntity -> {
            CompleteTask completeTask = createCompleteTask(CompletedTaskType.Follow_Up
                    , actionTransitionEntity.getUuidDoctor()
                    , actionTransitionEntity.getUuidUser()
                    , actionTransitionEntity.getTransition().getNextFollowupDate());
            completeTasks.add(completeTask);
        });
        return completeTasks;
    }

    public List<CompleteTask> findCompletedFirstVisitTask(String clinicId) {
        List<CompleteTask> completeTasks = new ArrayList<>();
        List<ActionTransitionEntity> result = actionTransitionRepository.findCompletedFirstVisitTask(clinicId);
        result.stream().forEach(actionTransitionEntity -> {
            CompleteTask completeTask = createCompleteTask(CompletedTaskType.First_Visit
                    , actionTransitionEntity.getUuidDoctor()
                    , actionTransitionEntity.getUuidUser()
                    , actionTransitionEntity.getTransition().getNextFollowupDate());
            completeTasks.add(completeTask);
        });
        return completeTasks;
    }

    public CompleteTask createCompleteTask(CompletedTaskType completedTaskType,
                                           String doctorUUID,
                                           String userUUID,
                                           Long NextFollowupDate) {
        String doctorData = doctorCacheService.getDoctorByUUID(doctorUUID);
        String userName = userCacheService.getUserByUUID(userUUID);
        CompleteTask completeTask = new CompleteTask();
        completeTask.setDoctorName(doctorData.split(",")[0]);
        completeTask.setDoctorNPI(doctorData.split(",")[1]);
        completeTask.setUserName(userName);
        completeTask.setNextFollowupDate(NextFollowupDate);
        completeTask.setCompletedTaskType(completedTaskType);
        return completeTask;
    }

}
