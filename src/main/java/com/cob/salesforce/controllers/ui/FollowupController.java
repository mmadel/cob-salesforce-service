package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.models.followup.FollowupModel;
import com.cob.salesforce.services.followup.FollowUpHistoryService;
import com.cob.salesforce.services.followup.FollowupDoctorFinderService;
import com.cob.salesforce.services.followup.FollowupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/followup")
public class FollowupController {

    @Autowired
    FollowupService followupService;
    @Autowired
    FollowupDoctorFinderService followupDoctorFinderService;
    @Autowired
    FollowUpHistoryService followUpHistoryService;

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
    public ResponseEntity getFollowupDoctors(@RequestParam(name = "offset") String offset,
                                             @RequestParam(name = "limit") String limit, @PathVariable(name = "clinicId") String clinicId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return new ResponseEntity<>(followupDoctorFinderService.findFollowupDoctor(paging, clinicId), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/history/doctorUUID/{doctorUUID}/clinicId/{clinicId}")
    public ResponseEntity getFollowupDoctorHistory(@PathVariable(name = "doctorUUID") String doctorUUID, @PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity<>(followUpHistoryService.showDoctorFollowupHistory(doctorUUID, clinicId), HttpStatus.OK);
    }

}
