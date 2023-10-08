package com.cob.salesforce.controllers;

import com.cob.salesforce.services.workflow.FirstTimeService;
import com.cob.salesforce.services.workflow.FollowupService;
import com.cob.salesforce.services.workflow.IntakeService;
import com.cob.salesforce.services.workflow.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    IntakeService intakeService;
    @Autowired
    FirstTimeService firstTimeService;
    @Autowired
    FollowupService followupService;

    @Autowired
    SchedulerService schedulerService;

    @ResponseBody
    @GetMapping("/intake")
    public void intake() {
        intakeService.execute("dadc723e-6547-11ee-8c99-0242ac120002", "1e2e9058-6548-11ee-8c99-0242ac120002");
    }

    @ResponseBody
    @GetMapping("/first/time")
    public void firstTime() {
        firstTimeService.execute("dadc723e-6547-11ee-8c99-0242ac120002", "1e2e9058-6548-11ee-8c99-0242ac120002");
    }

    @ResponseBody
    @GetMapping("/followup")
    public void followup() {
        followupService.execute("dadc723e-6547-11ee-8c99-0242ac120002", "1e2e9058-6548-11ee-8c99-0242ac120002");
    }

    @ResponseBody
    @GetMapping("/scheduler")
    public void scheduler() {
        schedulerService.execute("5d7631d2-6604-11ee-8c99-0242ac120002");
    }
}
