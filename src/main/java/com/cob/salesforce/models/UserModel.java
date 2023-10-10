package com.cob.salesforce.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModel {
    private Long id;
    private String uuid;
    private String name;
    private Long createdAt;
}
