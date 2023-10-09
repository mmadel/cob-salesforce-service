package com.cob.salesforce.controllers;

import com.cob.salesforce.services.transition.impl.FirstTimeTransitionService;
import com.cob.salesforce.services.transition.impl.FollowupTransitionService;
import com.cob.salesforce.services.transition.impl.IntakeTransitionService;
import com.cob.salesforce.services.transition.impl.SchedulerTransitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    IntakeTransitionService intakeTransitionService;
    @Autowired
    FirstTimeTransitionService firstTimeTransitionService;
    @Autowired
    FollowupTransitionService followupTransitionService;

    @Autowired
    SchedulerTransitionService schedulerTransitionService;

    @ResponseBody
    @GetMapping("/intake")
    public void intake() {
        //intakeTransitionService.execute("dadc723e-6547-11ee-8c99-0242ac120002", "1e2e9058-6548-11ee-8c99-0242ac120002");
    }

    @ResponseBody
    @GetMapping("/first/time")
    public void firstTime() {
        //firstTimeTransitionService.execute("dadc723e-6547-11ee-8c99-0242ac120002", "1e2e9058-6548-11ee-8c99-0242ac120002");
    }

    @ResponseBody
    @GetMapping("/followup")
    public void followup() {
        //followupTransitionService.execute("dadc723e-6547-11ee-8c99-0242ac120002", "1e2e9058-6548-11ee-8c99-0242ac120002");
    }

    @ResponseBody
    @GetMapping("/scheduler")
    public void scheduler() {
        schedulerTransitionService.execute("5d7631d2-6604-11ee-8c99-0242ac120002");
    }
}
