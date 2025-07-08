package com.khanh.airbnb.repositories;

import com.khanh.airbnb.domain.entities.HomestayAvailabilityEntity;
import com.khanh.airbnb.domain.entities.HomestayEntity;
import com.khanh.airbnb.domain.enums.BookingStatus;
import com.khanh.airbnb.domain.enums.HomestayStatus;
import com.khanh.airbnb.domain.keys.HomestayAvailabilityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HomestayAvailabilityRepository extends JpaRepository<HomestayAvailabilityEntity, HomestayAvailabilityKey> {
    boolean existsByHomestayEntityAndIdDateBetweenAndStatusIn(
            HomestayEntity homestayEntity,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            List<HomestayStatus> statuses
    );
}
