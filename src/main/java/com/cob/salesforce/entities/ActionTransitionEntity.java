package com.cob.salesforce.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "action_transition")
@Setter
@Getter
public class ActionTransitionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "action_id")
    private ActionEntity action;
    @ManyToOne
    @JoinColumn(name = "transition_id")
    private TransitionEntity transition;

    private String uuidUser;

    private String uuidDoctor;
    private Long createdAt;

    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }
}
