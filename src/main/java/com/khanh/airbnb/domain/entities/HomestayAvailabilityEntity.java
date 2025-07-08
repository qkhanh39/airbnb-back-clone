package com.khanh.airbnb.domain.entities;

import com.khanh.airbnb.domain.enums.BookingStatus;
import com.khanh.airbnb.domain.enums.HomestayStatus;
import com.khanh.airbnb.domain.keys.HomestayAvailabilityKey;
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
@Table(name = "homestay_availability")
public class HomestayAvailabilityEntity {

    @EmbeddedId
    private HomestayAvailabilityKey id;

    @ManyToOne
    @MapsId("homestayId")
    @JoinColumn(name = "homestay_id")
    private HomestayEntity homestayEntity;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private HomestayStatus status;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private BookingEntity bookingEntity;
    private String note;
}
