package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.models.followup.FollowupModel;
import com.cob.salesforce.services.followup.FollowupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/followup")
public class FollowupController {

    @Autowired
    FollowupService followupService;

    @PostMapping("/first")
    @ResponseBody
    public ResponseEntity createFirstFollowup(@RequestBody FollowupModel model) {
        return new ResponseEntity(followupService.createFollowup(model), HttpStatus.OK);
    }

    @PostMapping("/next")
    @ResponseBody
    public void createFollowup() {

    }
}
