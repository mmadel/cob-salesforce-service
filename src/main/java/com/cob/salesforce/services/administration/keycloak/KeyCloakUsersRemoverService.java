package com.cob.salesforce.services.administration.keycloak;

import com.cob.salesforce.exceptions.business.UserException;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KeyCloakUsersRemoverService {
    @Autowired
    Keycloak keycloakService;
    @Value("${kc.realm}")
    private String realm;

    public void removeKCUser(String userId) throws UserException {
        javax.ws.rs.core.Response response = keycloakService.realm(realm)
                .users().delete(userId);
        if(response.getStatus() == 404)
            throw new UserException(HttpStatus.NOT_FOUND, UserException.USER_NOT_FOUND, new Object[]{userId});
    }
}
