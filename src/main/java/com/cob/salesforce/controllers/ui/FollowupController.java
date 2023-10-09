package com.cob.salesforce.controllers.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/followup")
public class FollowupController {

    @PostMapping("/first")
    @ResponseBody
    public void createFirstFollowup() {

    }

    @PostMapping("/next")
    @ResponseBody
    public void createFollowup() {

    }
}
