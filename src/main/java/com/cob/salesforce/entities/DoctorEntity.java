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

    @Column
    private String name;
    @Column
    private String npi;
    @Column
    private String uuid;
    @Column
    private Long createdAt;

    @Column
    private String clinicId;

    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }
}
