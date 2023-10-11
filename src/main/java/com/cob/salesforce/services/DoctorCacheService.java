package com.cob.salesforce.services;

import com.cob.salesforce.entities.DoctorEntity;
import com.cob.salesforce.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DoctorCacheService {
    @Autowired
    DoctorRepository doctorRepository;
    Map<String, String> doctors = new HashMap<>();

    private void fillDoctorsMap() {
        doctorRepository.findAll().forEach(doctorEntity -> {
            String doctorData = doctorEntity.getName() + ',' + doctorEntity.getNpi();
            doctors.put(doctorEntity.getUuid(), doctorData);
        });
    }

    public String getDoctorByUUID(String uuid) {
        if (doctors.size() == 0){
            fillDoctorsMap();
        }

        return doctors.get(uuid);
    }

    public void cacheDoctor(DoctorEntity doctorEntity) {
        String doctorData = doctorEntity.getName() + ',' + doctorEntity.getNpi();
        doctors.put(doctorEntity.getUuid(), doctorData);
    }
}
