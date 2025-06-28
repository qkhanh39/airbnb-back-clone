package com.khanh.airbnb.domain.repositories;

import com.khanh.airbnb.domain.entities.WardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends JpaRepository<WardEntity, Integer> {
}
