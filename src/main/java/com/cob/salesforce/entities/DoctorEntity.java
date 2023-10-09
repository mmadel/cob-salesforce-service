package com.cob.salesforce.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "doctor")
@Getter
@Setter
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;
    private String npi;
    private String uuid;
    private Long createdAt;
    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }
}
