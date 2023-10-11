package com.cob.salesforce.controllers.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/task")
public class CompleteTasksController {

    @ResponseBody
    @GetMapping("/complete/followup/clinicId/{clinicId}")
    public ResponseEntity getCompletedFollowupTask(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/complete/first/clinicId/{clinicId}")
    public ResponseEntity getCompletedFirstVisitTask(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
