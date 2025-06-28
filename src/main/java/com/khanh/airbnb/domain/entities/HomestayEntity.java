package com.khanh.airbnb.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "homestays")
public class HomestayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "homestay_id_seq")
    @SequenceGenerator(name = "homestay_id_seq", sequenceName = "homestay_id_seq", allocationSize = 1)
    @Column(name = "homestay_id")
    private Long homestayId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    private String name;
    private String description;
    private String address;

    private Integer wardId;

    private Integer districtId;
    private Integer cityId;

    @Column(name = "max_guests")
    private Integer maxGuests;

    @Column(name = "price_default")
    private Integer priceDefault;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
