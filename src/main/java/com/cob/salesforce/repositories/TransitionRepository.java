package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.TransitionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransitionRepository extends CrudRepository<TransitionEntity, Long> {
}
