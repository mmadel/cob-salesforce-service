package com.cob.salesforce.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorModel {
    private Long id;
    private String name;
    private String npi;
    private String uuid;
    private String clinicName;
    private String clinicId;
    private Long createdAt;
}
