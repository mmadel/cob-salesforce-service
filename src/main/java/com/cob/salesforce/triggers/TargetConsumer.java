package com.cob.salesforce.triggers;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.entities.UserEntity;
import com.cob.salesforce.entities.UserTargetEntity;
import com.cob.salesforce.models.intake.TargetDoctor;
import com.cob.salesforce.repositories.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TargetConsumer {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ActionTransitionRepository actionTransitionRepository;
    @Autowired
    UserTargetRepository userTargetRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClinicUserRepository clinicUserRepository;

    @RabbitListener(queues = "TARGET_Q")
    public void getUserAchievement(TargetDoctor targetDoctor) {
        AtomicReference<Integer> result = new AtomicReference<>(0);
        DoctorEntity doctor = doctorRepository.findByNameAndNpi(targetDoctor.getName(), targetDoctor.getNpi())
                .orElseThrow(() -> new IllegalArgumentException("doctor not found"));
        List<UserEntity> users = clinicUserRepository.findUsersByClinic(targetDoctor.getClinicId());
        users.stream().forEach(userEntity -> {
            Long firstFollowupDate = actionTransitionRepository.getDateOfFirstFollowUpByUser(targetDoctor.getClinicId(), doctor.getUuid(), userEntity.getUuid())
                    .stream().findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Error when getting DateOfFirstFollowUpByUser"));
            if (firstFollowupDate < targetDoctor.getCreatedDate()) {
                UserTargetEntity userTargetEntity = userTargetRepository.findUserByUUID(userEntity.getUuid());
                if (userTargetEntity == null) {
                    userTargetEntity = new UserTargetEntity();
                    userTargetEntity.setAchievement(0);
                    userTargetEntity.setFirstTime(0);
                    userTargetEntity.setUser(userEntity);
                }
                result.set(userTargetEntity.getAchievement() + 1);
                userTargetEntity.setAchievement(result.get());
                userTargetRepository.save(userTargetEntity);
            }
        });
    }
}
