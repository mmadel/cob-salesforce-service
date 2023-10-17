package com.cob.salesforce.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "followup_configuration")
@Setter
@Getter
public class FollowupConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_time_good")
    Integer firstTimeGood;
    @Column(name = "first_Time_neutral")
    Integer firstTimeNeutral;
    @Column(name = "first_time_not_Worth")
    Integer firstTimeNotWorth;
    @Column(name = "next_time_good")
    Integer nextTimeGood;
    @Column(name = "next_time_neutral")
    Integer nextTimeNeutral;
    @Column(name = "next_time_not_worth")
    Integer nextTimeNotWorth;
    @Column(name = "clinic_id")
    Integer clinicId;
}
