package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.models.DoctorModel;
import com.cob.salesforce.models.followup.FollowupModel;
import com.cob.salesforce.services.followup.FollowupDoctorFinderService;
import com.cob.salesforce.services.followup.FollowupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/followup")
public class FollowupController {

    @Autowired
    FollowupService followupService;
    @Autowired
    FollowupDoctorFinderService followupDoctorFinderService;

    @PostMapping("/first")
    @ResponseBody
    public ResponseEntity<Long> createFirstFollowup(@RequestBody FollowupModel model) {
        return new ResponseEntity<>(followupService.createFirstFollowup(model), HttpStatus.OK);
    }

    @PostMapping("/next")
    @ResponseBody
    public ResponseEntity<Long> createNextFollowup(@RequestBody FollowupModel model) {
        return new ResponseEntity<>(followupService.createFollowup(model), HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/doctors/clinicId/{clinicId}")
    public ResponseEntity<List<DoctorModel>> getFollowupDoctors(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity<>(followupDoctorFinderService.findFollowupDoctor(clinicId), HttpStatus.OK);
    }
}
