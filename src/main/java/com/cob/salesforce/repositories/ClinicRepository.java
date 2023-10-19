package com.cob.salesforce.repositories;

import com.cob.salesforce.entities.ClinicEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClinicRepository  extends CrudRepository<ClinicEntity,Long> {
}
