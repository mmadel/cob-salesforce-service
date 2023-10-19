package com.cob.salesforce.controllers.ui.administration;

import com.cob.salesforce.models.followup.configuration.FollowupConfiguration;
import com.cob.salesforce.services.followup.configuration.FollowupConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/followup/configure")
public class FollowupConfigurationController {

    @Autowired
    FollowupConfigurationService followupConfigurationService;
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity createFirstTimeConfiguration(@RequestBody FollowupConfiguration model){
        return new ResponseEntity(followupConfigurationService.createOrUpdate(model),HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity getAll(@PathVariable(name = "clinicId") Integer clinicId){
        return new ResponseEntity(followupConfigurationService.get(clinicId),HttpStatus.OK);
    }
}
