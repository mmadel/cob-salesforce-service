package com.cob.salesforce.models.followup;

import com.cob.salesforce.enums.Impression;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class FollowupDoctorHistory {
    Long visitedDate;
    Impression impression;
    String feedback;
    String userName;
}
