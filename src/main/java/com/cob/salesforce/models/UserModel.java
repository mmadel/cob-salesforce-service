package com.cob.salesforce.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserModel {
    private Long id;
    private String uuid;
    private String name;
    private Set<ActionModel> actions;
    private Long createdAt;
}
