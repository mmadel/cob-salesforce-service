package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.FollowupEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowupRepository extends CrudRepository<FollowupEntity, Long> {

    @Query("select fup from FollowupEntity fup where fup.transition.id =:transitionId and fup.clinicId =:clinicId and fup.doctor.id =:doctorId")
    FollowupEntity findFollowupByTransition(@Param("clinicId") String clinicId, @Param("transitionId") Long transitionId, @Param("doctorId") Long doctorId);
}
