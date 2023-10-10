package com.cob.salesforce.models.container;

import com.cob.salesforce.models.DoctorModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class DoctorListContainer {
    Integer number_of_records;
    Integer number_of_matching_records;

    List<DoctorModel> records;
}
