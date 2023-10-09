package com.cob.salesforce.models.followup;

import com.cob.salesforce.entities.UserEntity;
import com.cob.salesforce.enums.ContactPosition;
import com.cob.salesforce.enums.Impression;
import com.cob.salesforce.models.UserModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
public class FollowupModel {
    private Long dateOfVisit;

    private Impression impression;

    private String contactName;

    private ContactPosition contactPosition;

    private Long nextFollowupDate;

    private String feedback;

    private UserModel user;
}
