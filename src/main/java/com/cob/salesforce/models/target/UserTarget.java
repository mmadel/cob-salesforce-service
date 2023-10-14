package com.cob.salesforce.models.target;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserTarget {
    private Long id;
    private Integer firstTime;
    private Integer achievement;

    private String userUUID;
}
