package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionTransitionRepository extends CrudRepository<ActionTransitionEntity, Long> {

    @Query("select at.transition from ActionTransitionEntity at where at.uuidDoctor =:doctorUUID and at.transition.state = 'POTENTIAL'")
    TransitionEntity findPotentialTransitionByDoctor(@Param("doctorUUID") String doctorUUID);
}
