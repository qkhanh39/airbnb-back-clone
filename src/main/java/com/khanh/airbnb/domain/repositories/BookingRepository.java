package com.khanh.airbnb.domain.repositories;

import com.khanh.airbnb.domain.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
