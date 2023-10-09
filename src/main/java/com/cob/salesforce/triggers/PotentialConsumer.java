package com.cob.salesforce.triggers;

import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.intake.PotentialDoctor;
import com.cob.salesforce.services.intake.PotentialService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PotentialConsumer {
    @Autowired
    PotentialService potentialService;

    @RabbitListener(queues = "CLINIC1_POTENTIAL_Q")
    private void consumeIntakeMessage(PotentialDoctor potentialDoctor) {

        DoctorModel model = DoctorModel.builder()
                .name(potentialDoctor.getName())
                .npi(potentialDoctor.getNpi())
                .uuid(UUID.randomUUID().toString())
                .clinicName(potentialDoctor.getClinicName())
                .clinicId(potentialDoctor.getClinicId())
                .build();
        potentialService.createPotentialDoctor(model);
    }
}
