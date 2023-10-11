package com.cob.salesforce.services;

import com.cob.salesforce.entities.UserEntity;
import com.cob.salesforce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserCacheService {
    @Autowired
    UserRepository userRepository;
    Map<String, String> users = new HashMap<>();

    private void fillUsersMap() {
        userRepository.findAll().forEach(userEntity -> {
            String userName = userEntity.getName();
            users.put(userEntity.getUuid(), userName);
        });
    }

    public String getUserByUUID(String uuid) {
        if (users.size() == 0)
            fillUsersMap();
        return users.get(uuid);
    }

    public void cacheUser(UserEntity userEntity) {
        users.put(userEntity.getUuid(), userEntity.getName());
    }
}
