package com.cob.salesforce.configuration;

import com.cob.salesforce.services.DoctorCacheService;
import com.cob.salesforce.services.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    DoctorCacheService doctorCacheService;

    @Autowired
    UserCacheService userCacheService;
    @Override
    public void run(String... args) throws Exception {

    }
}
