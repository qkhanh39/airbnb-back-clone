package com.khanh.airbnb.repositories;

import com.khanh.airbnb.domain.entities.DistrictEntity;
import com.khanh.airbnb.domain.entities.WardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<WardEntity, Integer> {
    List<WardEntity> findByDistrictEntity(DistrictEntity district);
}
