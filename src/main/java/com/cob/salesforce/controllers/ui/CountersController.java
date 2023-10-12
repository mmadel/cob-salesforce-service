package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.models.dashboard.DashboardCounter;
import com.cob.salesforce.services.ui.CountersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/counter")
public class CountersController {
    @Autowired
    CountersService countersService;

    @ResponseBody
    @GetMapping("/potentials/doctors/clinicId/{clinicId}")
    public ResponseEntity<Integer> getPotentialsDoctors(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity<>(countersService.getPotentialsDoctors(clinicId), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/followup/doctors/clinicId/{clinicId}")
    public ResponseEntity<Integer> getFollowupDoctors(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity<>(countersService.getFollowUpDoctors(clinicId), HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/dashboard/clinicId/{clinicId}")
    public ResponseEntity<DashboardCounter> getDashboardCounter(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity<>(DashboardCounter.builder()
                .potentialDoctorsCounter(countersService.getPotentialsDoctors(clinicId))
                .followupDoctorsCounter(countersService.getFollowUpDoctors(clinicId))
                .build(), HttpStatus.OK);
    }

}
