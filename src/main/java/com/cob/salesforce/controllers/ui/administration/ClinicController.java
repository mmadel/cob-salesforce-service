package com.cob.salesforce.controllers.ui.administration;

import com.cob.salesforce.models.ClinicModel;
import com.cob.salesforce.services.administration.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clinic")
public class ClinicController {
    @Autowired
    ClinicService clinicService;
    @PostMapping(path = "/create")
    @ResponseBody
    public ResponseEntity create(@RequestBody ClinicModel model) {
        return new ResponseEntity(clinicService.create(model), HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    @ResponseBody
    public ResponseEntity getAll() {
        return new ResponseEntity(clinicService.findAll(), HttpStatus.OK);
    }
}
