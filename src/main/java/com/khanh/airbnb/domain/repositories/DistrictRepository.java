package com.khanh.airbnb.domain.repositories;

import com.khanh.airbnb.domain.entities.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer> {
}
