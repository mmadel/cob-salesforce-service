package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.UserTargetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserTargetRepository extends CrudRepository<UserTargetEntity, Long> {
    @Query("select ut from UserTargetEntity ut where ut.user.uuid =:uuid")
    UserTargetEntity findUserByUUID(@Param("uuid") String uuid);
}
