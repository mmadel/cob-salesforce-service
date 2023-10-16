
package com.cob.salesforce.models.security;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class KeyCloakUser {
    private String userId;

    private String username;
    private String firstName;
    private String lastName;

    private String password;
    private String email;
    private String address ;
    private List<String> roles;

}
