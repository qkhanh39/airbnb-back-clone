package com.khanh.airbnb.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "districts")
public class DistrictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_id_seq")
    @SequenceGenerator(name = "district_id_seq", sequenceName = "district_id_seq")
    @Column(name = "district_id")
    private Integer districtId;
    private String name;
}
