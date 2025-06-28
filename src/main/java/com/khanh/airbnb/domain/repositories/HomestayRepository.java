package com.khanh.airbnb.domain.repositories;

import com.khanh.airbnb.domain.entities.HomestayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface HomestayRepository extends JpaRepository<HomestayEntity, Long> {
}
