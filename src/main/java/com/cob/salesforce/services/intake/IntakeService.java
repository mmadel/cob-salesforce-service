package com.cob.salesforce.services.intake;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.intake.PotentialDoctor;
import com.cob.salesforce.repositories.DoctorRepository;
import com.cob.salesforce.services.transition.impl.IntakeTransitionService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @RabbitListener(queues = "CLINIC1_POTENTIAL_Q")
    private void consumeIntakeMessage(PotentialDoctor potentialDoctor) {

        DoctorModel model = DoctorModel.builder()
                .name(potentialDoctor.getName())
                .npi(potentialDoctor.getNpi())
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
