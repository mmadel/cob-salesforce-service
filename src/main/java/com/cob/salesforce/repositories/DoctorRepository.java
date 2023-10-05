package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.DoctorEntity;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<DoctorEntity, Long> {
}
