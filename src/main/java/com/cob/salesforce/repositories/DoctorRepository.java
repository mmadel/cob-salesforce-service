package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.DoctorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends CrudRepository<DoctorEntity, Long> {

    @Query("select doc from DoctorEntity doc where doc.uuid in :uuids")
    Page<DoctorEntity> findDoctorsByUUIDs(Pageable pageable, @Param("uuids") List<String> uuids);

    Optional<DoctorEntity> findByUuid(String uuid);

    Optional<DoctorEntity> findByNameAndNpi(String name, String npi);

}
