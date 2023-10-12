package com.cob.salesforce.triggers;

import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.intake.PotentialDoctor;
import com.cob.salesforce.services.potential.PotentialService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PotentialConsumer {
    @Autowired
    PotentialService potentialService;

    @RabbitListener(queues = "POTENTIAL_Q")
    private void consumeIntakeMessage(PotentialDoctor potentialDoctor) {
        DoctorModel model = new DoctorModel();
        model.setName(potentialDoctor.getName());
        model.setNpi(potentialDoctor.getNpi());
        model.setUuid(UUID.randomUUID().toString());
        model.setClinicName(potentialDoctor.getClinicName());
        model.setClinicId(potentialDoctor.getClinicId());
        potentialService.createPotentialDoctor(model);
    }
}
