package com.cob.salesforce.services.followup;

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
public class FollowupDoctorFinderService {
    @Autowired
    TransitionRepository transitionRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ModelMapper mapper;

    public List<DoctorModel> findFollowupDoctor(String clinicId) {
        List<DoctorEntity> followupDoctors = null;
        List<DoctorModel> models = null;
        List<String> uuids = transitionRepository.findUUIDFollowupDoctors(clinicId);
        if (uuids.size() > 0)
            followupDoctors = doctorRepository.findDoctorsByUUIDs(uuids);
        if (followupDoctors != null)
            models = followupDoctors.stream().map(doctorEntity -> mapper.map(doctorEntity, DoctorModel.class)).collect(Collectors.toList());
        return models;
    }
}
