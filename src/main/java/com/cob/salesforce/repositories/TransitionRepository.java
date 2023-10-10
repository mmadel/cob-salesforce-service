package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.TransitionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransitionRepository extends CrudRepository<TransitionEntity, Long> {
    @Query("select tran from TransitionEntity as tran where tran.nextFollowupDate < :fireDate and tran.state = 'COMPLETE'")
    List<TransitionEntity> findFollowUpDoctors(@Param("fireDate") Long fireDate);

    @Query("select count(tran.id) from TransitionEntity as tran where tran.state = 'POTENTIAL' and tran.clinicId =:clinicId")
    Integer findPotentialDoctors(@Param("clinicId") String clinicId);

    @Query("select count(tran.id) from TransitionEntity as tran where tran.state = 'FOLLOWUP' and tran.clinicId =:clinicId")
    Integer findFollowupDoctors(@Param("clinicId") String clinicId);

}
