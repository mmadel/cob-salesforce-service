package com.cob.salesforce.models.followup.configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FollowupConfiguration {
    Long id;
    Integer firstTimeGood;
    Integer firstTimeNeutral;
    Integer firstTimeNotWorth;
    Integer nextTimeGood;
    Integer nextTimeNeutral;
    Integer nextTimeNotWorth;
    Integer clinicId;
}