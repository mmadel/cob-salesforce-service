package com.cob.salesforce.services.potential;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.repositories.DoctorRepository;
import com.cob.salesforce.repositories.TransitionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PotentialDoctorFinderService {
    @Autowired
    TransitionRepository transitionRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ModelMapper mapper;

    public List<DoctorModel> findPotentialDoctor(String clinicId) {
        List<DoctorEntity> doctors = null;
        List<DoctorModel> models = null;
        List<String> uuids = transitionRepository.findUUIDPotentialDoctors(clinicId);
        if (uuids.size() > 0)
            doctors = doctorRepository.findDoctorsByUUIDs(uuids);
        if (doctors != null)
            models = doctors.stream().map(doctorEntity -> mapper.map(doctorEntity, DoctorModel.class)).collect(Collectors.toList());
        return models;
    }
}
