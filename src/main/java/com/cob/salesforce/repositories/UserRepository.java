package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUuid(String uuid);
}
