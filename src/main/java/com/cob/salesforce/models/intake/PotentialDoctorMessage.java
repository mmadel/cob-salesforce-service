package com.cob.salesforce.models.intake;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PotentialDoctorMessage implements Serializable {
    private String name;
    private String npi;
}
