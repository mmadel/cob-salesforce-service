package com.cob.salesforce.services.potential;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.repositories.DoctorRepository;
import com.cob.salesforce.services.DoctorCacheService;
import com.cob.salesforce.services.transition.impl.IntakeTransitionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;


@Service
public class PotentialService {
    @Autowired
    ModelMapper mapper;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    IntakeTransitionService intakeTransitionService;
    @Autowired
    DoctorCacheService doctorCacheService;
    public void createPotentialDoctor(DoctorModel doctorModel){
        createDoctor(doctorModel);
        startTransitionEngine(doctorModel.getUuid(),doctorModel.getClinicId());
    }
    @CachePut(value="doctors",key = "#model.uuid")
    public DoctorEntity createDoctor(DoctorModel model) {
        return doctorRepository.save(mapper.map(model, DoctorEntity.class));
    }

    private void startTransitionEngine(String doctorUUID,String clinicId) {
        //will be read from security
        String userUUID = "5c0c0f14-6614-11ee-8c99-0242ac120002";
        intakeTransitionService.execute(userUUID, doctorUUID , clinicId);
    }
}
