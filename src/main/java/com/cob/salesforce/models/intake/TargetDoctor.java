package com.cob.salesforce.models.intake;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TargetDoctor {
    private String name;
    private String npi;
    private String clinicId;
    private Long createdDate;
}
