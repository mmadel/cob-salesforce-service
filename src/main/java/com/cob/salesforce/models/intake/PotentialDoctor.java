package com.cob.salesforce.models.intake;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PotentialDoctor{
    private String name;
    private String npi;

    @Override
    public String toString() {
        return "PotentialDoctor{" +
                "name='" + name + '\'' +
                ", npi='" + npi + '\'' +
                '}';
    }
}
