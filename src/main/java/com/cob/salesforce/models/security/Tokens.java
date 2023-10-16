package com.cob.salesforce.models.security;

import lombok.Data;

@Data
public class Tokens {
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String refresh_expires_in;
    private String token_type;
}
