package com.cob.salesforce.models;

import com.cob.salesforce.enums.State;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransitionModel {
    private Long id;
    private State state;

    Long createdAt;
}
