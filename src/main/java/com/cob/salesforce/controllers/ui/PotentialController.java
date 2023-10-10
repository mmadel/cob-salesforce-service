package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.services.potential.PotentialDoctorFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity getPotentialDoctors(@RequestParam(name = "offset") String offset,
                                              @RequestParam(name = "limit") String limit, @PathVariable(name = "clinicId") String clinicId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return new ResponseEntity(potentialDoctorFinderService.findPotentialDoctor(paging,clinicId), HttpStatus.OK);
    }
}
