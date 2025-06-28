package com.khanh.airbnb.domain.keys;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HomestayAvailabilityKey implements Serializable {
    private Long homestayId;
    private LocalDate date;
}
