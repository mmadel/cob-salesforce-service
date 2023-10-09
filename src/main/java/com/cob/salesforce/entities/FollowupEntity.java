package com.cob.salesforce.entities;

import com.cob.salesforce.enums.ActionType;
import com.cob.salesforce.enums.ContactPosition;
import com.cob.salesforce.enums.Impression;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "followup")
@Getter
@Setter
public class FollowupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private Long dateOfVisit;
    @Enumerated(EnumType.STRING)
    @Column(name = "impression")
    private Impression impression;
    @Column(name = "contact_name")
    private String contactName;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_position")
    private ContactPosition contactPosition;

    @Column(name = "next_followup_date")
    private Long nextFollowupDate;

    @Column(name = "feedback")
    private String feedback;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorEntity doctor;
}
