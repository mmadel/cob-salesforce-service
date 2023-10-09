package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActionTransitionRepository extends CrudRepository<ActionTransitionEntity, Long> {

    @Query("select at.transition from ActionTransitionEntity at where at.uuidDoctor =:doctorUUID and at.transition.state = 'POTENTIAL' and at.transition.clinicId =:clinicId")
    TransitionEntity findPotentialTransitionByDoctor(@Param("doctorUUID") String doctorUUID,@Param("clinicId") String clinicId);
}
