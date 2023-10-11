package com.cob.salesforce.services;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class DoctorCacheService {
    @Autowired
    DoctorRepository doctorRepository;

    @Cacheable(cacheNames = "doctors" , key = "#uuid")
    public DoctorEntity getDoctorByUUID(String uuid){
        return doctorRepository.findByUuid(uuid).get();
    }
}
