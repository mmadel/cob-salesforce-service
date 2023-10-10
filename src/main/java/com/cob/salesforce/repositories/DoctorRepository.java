package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.DoctorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends CrudRepository<DoctorEntity, Long> {

    @Query("select doc from DoctorEntity doc where doc.uuid in :uuids")
    List<DoctorEntity> findDoctorsByUUIDs(@Param("uuids") List<String> uuids);
}
