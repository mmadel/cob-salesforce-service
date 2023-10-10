package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.services.potential.PotentialDoctorFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/potential")
public class PotentialController {
    @Autowired
    PotentialDoctorFinderService potentialDoctorFinderService;

    @ResponseBody
    @GetMapping("/doctors/clinicId/{clinicId}")
    public ResponseEntity getPotentialDoctors(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity(potentialDoctorFinderService.findPotentialDoctor(clinicId), HttpStatus.OK);
    }
}
