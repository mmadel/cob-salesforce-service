package com.cob.salesforce.models;

import com.cob.salesforce.entities.TransitionEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;

@Setter
@Getter
public class DoctorModel {
    private Long id;

    private String name;
    private String npi;
    private Set<TransitionModel> transitions;
    private Long createdAt;
}
