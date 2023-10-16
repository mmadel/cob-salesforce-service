package com.cob.salesforce.services.administration.keycloak;

import com.cob.salesforce.exceptions.business.UserException;
import com.cob.salesforce.exceptions.business.UserKeyCloakException;
import com.cob.salesforce.models.security.Credentials;
import com.cob.salesforce.models.security.KeyCloakUser;
import com.cob.salesforce.services.security.AuthenticationService;
import com.cob.salesforce.utils.Encryption;
import com.google.gson.Gson;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class KeyCloakUsersCreatorService {
    @Autowired
    Keycloak keycloakService;


    @Value("${kc.url}")
    private String keycloakURL;

    @Value("${kc.realm}")
    private String realm;
    @Autowired
    Encryption encryption;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    KeyCloakUsersRemoverService keyCloakUsersRemoverService;

    @Autowired
    AuthenticationService authenticationService;
    @Value("${kc.administrator.username}")
    private String admin_username;
    @Value("${kc.administrator.password}")
    private String admin_password;
    public UserRepresentation create(KeyCloakUser keyCloakUser) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, WebApplicationException {
return null;
    }
    private List<RoleRepresentation> prepareRoleRepresentation(List<String> roles, RealmResource realmResource, ClientRepresentation clientRepresentation) {
        List<RoleRepresentation> roleRepresentation = new ArrayList<>();
        roles.forEach(role -> {
            RoleRepresentation clientRole = realmResource.clients().get(clientRepresentation.getId())
                    .roles().get(role).toRepresentation();
            roleRepresentation.add(clientRole);
        });
        return roleRepresentation;
    }
    private UserRepresentation prepareUserRepresentation(KeyCloakUser keyCloakUser) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(keyCloakUser.getUsername());
        user.setFirstName(keyCloakUser.getFirstName());
        user.setLastName(keyCloakUser.getLastName());
        user.setEmail(keyCloakUser.getEmail());
        return user;
    }
    private void updateAttribute(UserResource userResource, String property, String value) {
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.singleAttribute(property, value);
        userResource.update(userRepresentation);
    }
    private void setUserPassword(String userId, String password) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UserException, HttpClientErrorException, UserKeyCloakException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticationService.getTokens(Credentials.builder()
                .userName(admin_username)
                .password(admin_password)
                .build()).getAccess_token());
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(encryption.decrypt(password));
        Gson gson = new Gson();
        gson.toJson(credential);
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(credential).toString(), headers);
        try {
            restTemplate.put(keycloakURL + "/admin/realms/" + realm + "/users/" + userId + "/reset-password", httpEntity);
        } catch (HttpClientErrorException.BadRequest exception) {
            keyCloakUsersRemoverService.removeKCUser(userId);
            String message = exception.getMessage().split(":")[4].split("\"")[0];
            throw new UserKeyCloakException(HttpStatus.BAD_REQUEST, UserKeyCloakException.INVALID_PASSWORD, new Object[]{message});
        }


    }
}
