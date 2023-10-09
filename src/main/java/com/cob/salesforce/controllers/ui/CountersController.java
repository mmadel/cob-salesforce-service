package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.services.ui.CountersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/counter")
public class CountersController {
    @Autowired
    CountersService countersService;
    @ResponseBody
    @GetMapping("/potentials/doctors")
    public ResponseEntity<Integer> getPotentialsDoctors() {
        return new ResponseEntity<>(countersService.getPotentialsDoctors(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/followup/doctors")
    public ResponseEntity<Integer> getFollowupDoctors() {
        return new ResponseEntity<>(countersService.getFollowUpDoctors(), HttpStatus.OK);
    }
}
