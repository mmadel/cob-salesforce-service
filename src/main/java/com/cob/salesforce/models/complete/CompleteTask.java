package com.cob.salesforce.models.complete;

import com.cob.salesforce.enums.CompletedTaskType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompleteTask {
    private String doctorName;
    private String doctorNPI;
    private String userName;
    private Long nextFollowupDate;

    private CompletedTaskType completedTaskType;

}
