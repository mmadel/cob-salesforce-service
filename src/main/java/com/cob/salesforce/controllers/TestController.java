package com.cob.salesforce.controllers;

import com.cob.salesforce.BeanFactory;
import com.cob.salesforce.entities.*;
import com.cob.salesforce.enums.ActionType;
import com.cob.salesforce.enums.State;
import com.cob.salesforce.repositories.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @ResponseBody
    @GetMapping
    public void test() {
        UserRepository userRepository = BeanFactory.getBean(UserRepository.class);
        UserEntity user = new UserEntity();
        user.setName("ahmed");
        user.setUuid("dadc723e-6547-11ee-8c99-0242ac120002");
        UserEntity createdUser = userRepository.save(user);


        UserEntity intake = new UserEntity();
        user.setName("in-user");
        user.setUuid("dadc723e-6547-11ee-8c99-0242ac120002");
        UserEntity createdIntake = userRepository.save(intake);

        DoctorRepository doctorRepository = BeanFactory.getBean(DoctorRepository.class);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setName("wael");
        doctor.setNpi("4940404");
        doctor.setUuid("1e2e9058-6548-11ee-8c99-0242ac120002");

        DoctorEntity createdDoctor = doctorRepository.save(doctor);

        ActionEntity pushAction = new ActionEntity();
        pushAction.setActionType(ActionType.PUSH);
        pushAction.setUser(createdIntake);
        ActionRepository actionRepository = BeanFactory.getBean(ActionRepository.class);
        actionRepository.save(pushAction);

        TransitionEntity potentialTransition = new TransitionEntity();
        potentialTransition.setDoctor(createdDoctor);
        potentialTransition.setState(State.POTENTIAL);
        TransitionRepository transitionRepository = BeanFactory.getBean(TransitionRepository.class);
        transitionRepository.save(potentialTransition);

        ActionTransitionRepository actionTransitionRepository = BeanFactory.getBean(ActionTransitionRepository.class);
        ActionTransitionEntity actionTransition = new ActionTransitionEntity();
        actionTransition.setAction(pushAction);
        actionTransition.setTransition(potentialTransition);
        actionTransition.setUuidDoctor(createdDoctor.getUuid());
        actionTransition.setUuidUser(createdIntake.getUuid());
        actionTransitionRepository.save(actionTransition);
    }
}
