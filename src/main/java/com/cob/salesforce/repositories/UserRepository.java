package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
