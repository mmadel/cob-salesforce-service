package com.cob.salesforce.services;

import com.cob.salesforce.entities.UserEntity;
import com.cob.salesforce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserCacheService {
    @Autowired
    UserRepository userRepository;

    @Cacheable(cacheNames = "users" , key = "#uuid")
    public UserEntity getUserByUUID(String uuid){
        return userRepository.findByUuid(uuid).get();
    }
}
