package com.cob.salesforce.services;

import com.cob.salesforce.entities.UserEntity;
import com.cob.salesforce.entities.UserTargetEntity;
import com.cob.salesforce.models.UserModel;
import com.cob.salesforce.models.target.UserTarget;
import com.cob.salesforce.repositories.ClinicUserRepository;
import com.cob.salesforce.repositories.UserRepository;
import com.cob.salesforce.repositories.UserTargetRepository;
import com.cob.salesforce.services.ui.CountersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    ClinicUserRepository clinicUserRepository;

    @Autowired
    UserTargetRepository userTargetRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CountersService countersService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public List<UserModel> getUserByClinic(String clinicId) {
        return clinicUserRepository.findUsersByClinic(clinicId)
                .stream().map(userEntity -> {
                    return mapper.map(userEntity, UserModel.class);
                }).collect(Collectors.toList());
    }

    public Long updateFirstVisitUserTarget(UserTarget userTarget) {
        UserTargetEntity userTargetEntity = userTargetRepository.findUserByUUID(userTarget.getUserUUID());
        if (userTarget.getFirstTime() != null)
            userTargetEntity.setFirstTime(userTarget.getFirstTime());
        if (userTarget.getAchievement() != null)
            userTargetEntity.setAchievement(userTarget.getAchievement());
        UserEntity user = userRepository.findByUuid(userTarget.getUserUUID()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        userTargetEntity.setUser(user);
        UserTargetEntity created = userTargetRepository.save(userTargetEntity);
        Integer userFirstTimeVisitTarget = countersService.getUserFirstTimeVisitTarget(userTarget.getUserUUID());
        simpMessagingTemplate.convertAndSend("/topic/first/visit/target", userTarget.getUserUUID() + "_" +userFirstTimeVisitTarget);
        return created.getId();
    }
}
