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
        FollowupConfigurationEntity tobeCreated = new FollowupConfigurationEntity();
        tobeCreated.setClinicId(followupConfiguration.getClinicId());
        tobeCreated.setFollowupConfiguration(mapper.map(followupConfiguration, FollowupConfiguration.class));
        tobeCreated.setId(followupConfiguration.getId());
        return followupConfigurationRepository.save(tobeCreated).getId();
    }

    public FollowupConfiguration get(Integer clinicId) {
        Optional<FollowupConfigurationEntity> configurationEntityOptional = followupConfigurationRepository.findByClinicId(clinicId);
        if (!configurationEntityOptional.isEmpty())
            return buildFollowupConfiguration(configurationEntityOptional.get());
        else
            return FollowupConfiguration.builder().build();
    }

    private FollowupConfiguration buildFollowupConfiguration(FollowupConfigurationEntity entity) {
        return FollowupConfiguration.builder()
                .id(entity.getId())
                .firstTimeGood(entity.getFollowupConfiguration().getFirstTimeGood())
                .firstTimeNeutral(entity.getFollowupConfiguration().getFirstTimeNeutral())
                .firstTimeNotWorth(entity.getFollowupConfiguration().getFirstTimeNotWorth())
                .nextTimeGood(entity.getFollowupConfiguration().getNextTimeGood())
                .nextTimeNeutral(entity.getFollowupConfiguration().getNextTimeNeutral())
                .nextTimeNotWorth(entity.getFollowupConfiguration().getNextTimeNotWorth())
                .build();
    }
}
