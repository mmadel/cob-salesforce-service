package com.cob.salesforce.controllers.ui;

import com.cob.salesforce.exceptions.business.UserException;
import com.cob.salesforce.exceptions.business.UserKeyCloakException;
import com.cob.salesforce.models.UserModel;
import com.cob.salesforce.models.target.UserTarget;
import com.cob.salesforce.services.UserTargetService;
import com.cob.salesforce.services.administration.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserTargetService userTargetService;
    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity getPotentialDoctors(@PathVariable(name = "clinicId") String clinicId) {
        return new ResponseEntity(userTargetService.getUserByClinic(clinicId), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/target/update")
    public ResponseEntity updateUserTarget(@RequestBody UserTarget userTarget) {
        return new ResponseEntity(userTargetService.updateFirstVisitUserTarget(userTarget), HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity create(@RequestBody UserModel model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UserException, UserKeyCloakException {
        return new ResponseEntity(userService.create(model), HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    @ResponseBody
    public ResponseEntity getAll() {
        return new ResponseEntity(userService.getAll(), HttpStatus.OK);
    }

}
