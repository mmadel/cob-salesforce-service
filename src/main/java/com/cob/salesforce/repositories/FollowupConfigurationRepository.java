package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.FollowupConfigurationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FollowupConfigurationRepository extends CrudRepository<FollowupConfigurationEntity, Long> {

    Optional<FollowupConfigurationEntity> findByClinicId(Integer clinicId);
}
