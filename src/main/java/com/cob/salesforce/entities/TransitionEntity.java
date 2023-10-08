package com.cob.salesforce.entities;

import com.cob.salesforce.enums.State;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "transition")
@Getter
@Setter
public class TransitionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @OneToMany(mappedBy = "transition")
    private Set<ActionTransitionEntity> actionTransition;

    @Column(name = "next_follow_up_date")
    Long nextFollowupDate;

    @Column
    private String doctorUUID;
    Long createdAt;

    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }
}
