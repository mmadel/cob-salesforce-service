package com.cob.salesforce.triggers;

import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.intake.PotentialDoctor;
import com.cob.salesforce.services.potential.PotentialService;
import com.cob.salesforce.services.ui.CountersService;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Map;
import java.util.UUID;

@Service
public class PotentialConsumer {
    @Autowired
    PotentialService potentialService;
    @Autowired
    CountersService countersService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = "POTENTIAL_Q")
    private void consumeIntakeMessage(PotentialDoctor potentialDoctor) {
        DoctorModel model = new DoctorModel();
        model.setName(potentialDoctor.getName());
        model.setNpi(potentialDoctor.getNpi());
        model.setUuid(UUID.randomUUID().toString());
        model.setClinicName(potentialDoctor.getClinicName());
        model.setClinicId(potentialDoctor.getClinicId());
        potentialService.createPotentialDoctor(model);
        Integer numberOfPotentialDoctor = countersService.getPotentialsDoctors(potentialDoctor.getClinicId());
        System.out.println(numberOfPotentialDoctor);
        simpMessagingTemplate.convertAndSend("/topic/potential", numberOfPotentialDoctor);

    }

}
