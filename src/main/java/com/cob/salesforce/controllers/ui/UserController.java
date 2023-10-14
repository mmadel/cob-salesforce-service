package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.models.followup.FollowupModel;
import com.cob.salesforce.models.target.UserTarget;
import com.cob.salesforce.repositories.UserTargetRepository;
import com.cob.salesforce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;


    @ResponseBody
    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity getPotentialDoctors(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity(userService.getUserByClinic(clinicId), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/target/update")
    public ResponseEntity updateUserTarget(@RequestBody UserTarget userTarget) {
        return new ResponseEntity(userService.updateFirstVisitUserTarget(userTarget), HttpStatus.OK);
    }
}
