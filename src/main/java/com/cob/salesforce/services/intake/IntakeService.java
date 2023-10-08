package com.cob.salesforce.services.intake;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.intake.PotentialDoctorMessage;
import com.cob.salesforce.repositories.DoctorRepository;
import com.cob.salesforce.services.transition.impl.IntakeTransitionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class IntakeService {
    @Autowired
    ModelMapper mapper;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    IntakeTransitionService intakeTransitionService;

    private void consumeIntakeMessage() {
        //message from RabbitMQ
        PotentialDoctorMessage tmp = new PotentialDoctorMessage();
        tmp.setName("ahmed");
        tmp.setNpi("304944");
        DoctorModel model = DoctorModel.builder()
                .name(tmp.getName())
                .npi(tmp.getNpi())
                .uuid(UUID.randomUUID().toString())
                .build();
        createDoctor(model);
        startTransitionEngine(model.getUuid());
    }

    private void createDoctor(DoctorModel model) {
        doctorRepository.save(mapper.map(model, DoctorEntity.class));
    }

    private void startTransitionEngine(String doctorUUID) {
        //will be read from security
        String userUUID = "5c0c0f14-6614-11ee-8c99-0242ac120002";
        intakeTransitionService.execute(userUUID, doctorUUID);
    }
}
