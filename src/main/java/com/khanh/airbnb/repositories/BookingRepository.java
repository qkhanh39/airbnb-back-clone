package com.khanh.airbnb.repositories;

import com.khanh.airbnb.domain.entities.BookingEntity;
import com.khanh.airbnb.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByUserEntity(UserEntity user);

    BookingEntity findByUserEntityAndBookingId(UserEntity user, Long bookingId);
}
