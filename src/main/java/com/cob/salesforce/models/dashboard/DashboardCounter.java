package com.cob.salesforce.models.dashboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DashboardCounter {
    Integer potentialDoctorsCounter;
    Integer followupDoctorsCounter;
    Integer userAchievement;
}
