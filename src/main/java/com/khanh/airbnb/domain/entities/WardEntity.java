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
@Table(name = "wards")
public class WardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ward_id_seq")
    @SequenceGenerator(name = "ward_id_seq", sequenceName = "ward_id_seq")
    @Column(name = "ward_id")
    private Integer wardId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictEntity districtEntity;
}
