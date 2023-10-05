package com.cob.salesforce.models;

import com.cob.salesforce.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionModel {
    private Long id;
    private ActionType actionType;
    private Long createdAt;
}
