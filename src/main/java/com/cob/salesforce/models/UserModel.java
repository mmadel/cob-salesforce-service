package com.cob.salesforce.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserModel {
    private Long id;
    private String uuid;
    private String name;
    private String password;
    private String userRole;
    private Long createdAt;

    private List<ClinicModel> clinics;
}
