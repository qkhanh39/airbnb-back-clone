package com.khanh.airbnb.repositories;

import com.khanh.airbnb.domain.entities.CityEntity;
import com.khanh.airbnb.domain.entities.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer> {
    List<DistrictEntity> findByCityEntity(CityEntity city);
}
