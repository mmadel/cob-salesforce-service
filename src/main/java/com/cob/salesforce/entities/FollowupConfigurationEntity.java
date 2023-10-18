package com.cob.salesforce.entities;

import com.cob.salesforce.models.followup.configuration.FollowupConfiguration;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


import javax.persistence.*;

@Entity
@Table(name = "clinic_followup_configuration")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Setter
@Getter
public class FollowupConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "followup_configuration", columnDefinition = "json")
    @Type(type = "json")
    private FollowupConfiguration followupConfiguration;
    @Column(name = "clinic_id")
    Integer clinicId;
}
