package com.cob.salesforce.services.followup;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.container.DoctorListContainer;
import com.cob.salesforce.repositories.DoctorRepository;
import com.cob.salesforce.repositories.TransitionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public DoctorListContainer findFollowupDoctor(Pageable pageable, String clinicId) {
        List<DoctorEntity> followupDoctors = null;
        List<DoctorModel> models = null;
        Page<DoctorEntity> pages = null;
        List<String> uuids = transitionRepository.findUUIDFollowupDoctors(clinicId);
        if (uuids.size() > 0)
            pages = doctorRepository.findDoctorsByUUIDs(pageable, uuids);

        models = pages.getContent().stream().map(doctorEntity -> mapper.map(doctorEntity, DoctorModel.class)).collect(Collectors.toList());
        long total = (pages).getTotalElements();
        return getPatientListContainer(total, models);
    }

    private DoctorListContainer getPatientListContainer(long total, List<DoctorModel> records) {
        return DoctorListContainer.builder()
                .number_of_records((int) total)
                .number_of_matching_records(records.size())
                .records(records)
                .build();
    }
}
