package com.khanh.airbnb.domain.repositories;

import com.khanh.airbnb.domain.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
}
