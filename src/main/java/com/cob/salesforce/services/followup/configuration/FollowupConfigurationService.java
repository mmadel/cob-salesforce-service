package com.cob.salesforce.services.followup.configuration;

import com.cob.salesforce.entities.FollowupConfigurationEntity;
import com.cob.salesforce.models.followup.configuration.FollowupConfiguration;
import com.cob.salesforce.repositories.FollowupConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FollowupConfigurationService {

    @Autowired
    FollowupConfigurationRepository followupConfigurationRepository;
    @Autowired
    ModelMapper mapper;

    public Long createOrUpdate(FollowupConfiguration followupConfiguration) {
        FollowupConfigurationEntity tobeCreated = mapper.map(followupConfiguration, FollowupConfigurationEntity.class);
        return followupConfigurationRepository.save(tobeCreated).getId();
    }

    public FollowupConfiguration get(Integer clinicId) {
        Optional<FollowupConfigurationEntity> configurationEntityOptional = followupConfigurationRepository.findByClinicId(clinicId);
        if (!configurationEntityOptional.isEmpty())
            return mapper.map(followupConfigurationRepository.findByClinicId(clinicId).get(), FollowupConfiguration.class);
        else
            return new FollowupConfiguration();
    }
}
