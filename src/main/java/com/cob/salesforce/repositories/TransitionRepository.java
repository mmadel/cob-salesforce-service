package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.TransitionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransitionRepository extends CrudRepository<TransitionEntity, Long> {
    @Query("select tran from TransitionEntity as tran where tran.nextFollowupDate < :fireDate and tran.state = 'COMPLETE'")
    List<TransitionEntity> findFollowUpDoctors(@Param("fireDate") Long fireDate);

}
