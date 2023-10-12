package com.cob.salesforce.services.followup;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.entities.FollowupEntity;
import com.cob.salesforce.enums.ActionType;
import com.cob.salesforce.models.followup.FollowupDoctorHistory;
import com.cob.salesforce.repositories.ActionTransitionRepository;
import com.cob.salesforce.repositories.FollowupRepository;
import com.cob.salesforce.repositories.UserRepository;
import com.cob.salesforce.services.DoctorCacheService;
import com.cob.salesforce.services.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowUpHistoryService {
    @Autowired
    ActionTransitionRepository actionTransitionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorCacheService doctorCacheService;
    @Autowired
    FollowupRepository followupRepository;

    public List<FollowupDoctorHistory> showDoctorFollowupHistory(String doctorUUID, String clinicId) {
        List<ActionType> includedActionType = new ArrayList<>();
        includedActionType.add(ActionType.TOUCH);
        includedActionType.add(ActionType.VISIT_FOLLOW_UP);
        return actionTransitionRepository.getDoctorHistory(clinicId, doctorUUID, includedActionType)
                .stream()
                .map(actionTransitionEntity -> {
                    DoctorEntity doctorEntity = doctorCacheService.
                            getDoctorByUUID(actionTransitionEntity.getUuidDoctor());
                    FollowupEntity followupEntity = getFollowupFeedback(actionTransitionEntity.getTransition().getClinicId(), actionTransitionEntity.getId(), doctorEntity.getId());
                    return FollowupDoctorHistory.builder()
                            .visitedDate(actionTransitionEntity.getTransition().getCreatedAt())
                            .impression(followupEntity != null ? followupEntity.getImpression() : null)
                            .feedback(followupEntity != null ? followupEntity.getFeedback() : null)
                            .userName(userRepository.findByUuid(actionTransitionEntity.getUuidUser()).orElseThrow(() -> new IllegalArgumentException("User not found - " + actionTransitionEntity.getUuidUser())).getName())
                            .build();

                }).collect(Collectors.toList());
    }

    private FollowupEntity getFollowupFeedback(String clinicId, Long actionTransitionId, Long doctorId) {
        return followupRepository.findFollowupByTransition(clinicId, actionTransitionId, doctorId);
    }
}
