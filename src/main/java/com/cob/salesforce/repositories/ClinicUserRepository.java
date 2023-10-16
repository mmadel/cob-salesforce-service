package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.ClinicUserEntity;
import com.cob.salesforce.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClinicUserRepository extends CrudRepository<ClinicUserEntity, Long> {

    @Query("select cu.user from ClinicUserEntity cu where cu.clinicId =:clinicId")
    List<UserEntity> findUsersByClinic(@Param("clinicId") String clinicId);

    @Query("select uc from ClinicUserEntity uc where uc.user.uuid in :ids")
    List<ClinicUserEntity> findByUsers(@Param("ids") List<String> ids);

    @Query("select uc from ClinicUserEntity uc where uc.user.uuid  =:uuid")
    Optional<List<ClinicUserEntity>> findByUserUUID(@Param("uuid") String uuid);
}
