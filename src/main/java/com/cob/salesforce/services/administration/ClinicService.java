package com.cob.salesforce.services.administration;

import com.cob.salesforce.entities.ClinicEntity;
import com.cob.salesforce.models.ClinicModel;
import com.cob.salesforce.repositories.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ClinicService {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public Long create(ClinicModel clinicModel) {
        return clinicRepository.save(mapper.map(clinicModel, ClinicEntity.class)).getId();
    }

    public List<ClinicModel> findAll() {
        return StreamSupport
                .stream(Spliterators
                        .spliteratorUnknownSize(clinicRepository.findAll().iterator(), 0), false)
                .map(clinicEntity -> {
                    return mapper.map(clinicEntity, ClinicModel.class);
                })
                .collect(Collectors.toList());
    }
}
