package com.cob.salesforce.models.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Credentials {
    private String userName;
    private String password;
}
