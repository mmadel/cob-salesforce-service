package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import com.cob.salesforce.enums.ActionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionTransitionRepository extends CrudRepository<ActionTransitionEntity, Long> {

    @Query("select at.transition from ActionTransitionEntity at where at.uuidDoctor =:doctorUUID and at.transition.state = 'POTENTIAL' and at.transition.clinicId =:clinicId")
    TransitionEntity findPotentialTransitionByDoctor(@Param("doctorUUID") String doctorUUID, @Param("clinicId") String clinicId);

    @Query("select at.transition from ActionTransitionEntity at where at.uuidDoctor =:doctorUUID and at.transition.state = 'FOLLOWUP' and at.transition.clinicId =:clinicId")
    TransitionEntity findFollowTransitionByDoctor(@Param("doctorUUID") String doctorUUID, @Param("clinicId") String clinicId);

    @Query("select at from ActionTransitionEntity at where at.uuidDoctor =:doctorUUID and at.transition.clinicId =:clinicId and at.action.actionType IN :actionTypes")
    List<ActionTransitionEntity> getDoctorHistory(@Param("clinicId") String clinicId, @Param("doctorUUID") String doctorUUID, @Param("actionTypes") List<ActionType> actionTypes);

    @Query("select at.createdAt from ActionTransitionEntity at where at.uuidDoctor =:doctorUUID and at.transition.clinicId =:clinicId " +
            "and at.uuidUser =:userUUID and at.action.actionType ='VISIT_FOLLOW_UP' " +
            "order by at.createdAt asc")
    List<Long> getDateOfFirstFollowUpByUser(@Param("clinicId") String clinicId,
                                      @Param("doctorUUID") String doctorUUID,
                                      @Param("userUUID") String userUUID);
}
