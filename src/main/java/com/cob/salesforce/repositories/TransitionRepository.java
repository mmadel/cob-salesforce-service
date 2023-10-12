package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.ActionTransitionEntity;
import com.cob.salesforce.entities.TransitionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransitionRepository extends PagingAndSortingRepository<TransitionEntity, Long> {
    @Query("select tran from TransitionEntity as tran where tran.nextFollowupDate < :fireDate and (tran.state = 'COMPLETE' or tran.state = 'INITIALIZE')")
    List<TransitionEntity> findFollowUpDoctors(@Param("fireDate") Long fireDate);

    @Query("select count(tran.id) from TransitionEntity as tran where tran.state = 'POTENTIAL' and tran.clinicId =:clinicId")
    Integer findPotentialDoctorsCounter(@Param("clinicId") String clinicId);

    @Query("select count(tran.id) from TransitionEntity as tran where tran.state = 'FOLLOWUP' and tran.clinicId =:clinicId")
    Integer findFollowupDoctorsCounter(@Param("clinicId") String clinicId);
    @Query("select tran.doctorUUID from TransitionEntity as tran where tran.state = 'POTENTIAL' and tran.clinicId =:clinicId")
    List<String> findUUIDPotentialDoctors( @Param("clinicId") String clinicId);

    @Query("select tran.doctorUUID from TransitionEntity as tran where tran.state = 'FOLLOWUP' and tran.clinicId =:clinicId")
    List<String> findUUIDFollowupDoctors(@Param("clinicId") String clinicId);

    @Query("select at from TransitionEntity at where at.state ='COMPLETE' and at.clinicId =:clinicId")
    List<TransitionEntity> findCompletedFollowupTask(@Param("clinicId") String clinicId);

    @Query("select at from TransitionEntity at where at.state ='INITIALIZE' and at.clinicId =:clinicId")
    List<TransitionEntity> findCompletedFirstVisitTask(@Param("clinicId") String clinicId);
}
