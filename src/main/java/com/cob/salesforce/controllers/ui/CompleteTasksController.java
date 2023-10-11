package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.services.complete.CompleteTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/task")
public class CompleteTasksController {

    @Autowired
    CompleteTaskService completeTaskService;

    @ResponseBody
    @GetMapping("/complete/followup/clinicId/{clinicId}")
    public ResponseEntity getCompletedFollowupTask(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity(completeTaskService.findCompletedFollowupTask(clinicId), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/complete/first/clinicId/{clinicId}")
    public ResponseEntity getCompletedFirstVisitTask(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity(completeTaskService.findCompletedFirstVisitTask(clinicId), HttpStatus.OK);
    }
}
