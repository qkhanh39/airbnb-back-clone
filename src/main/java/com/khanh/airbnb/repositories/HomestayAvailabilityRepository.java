package com.khanh.airbnb.domain.repositories;

import com.khanh.airbnb.domain.entities.HomestayAvailabilityEntity;
import com.khanh.airbnb.domain.keys.HomestayAvailabilityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomestayAvailabilityRepository extends JpaRepository<HomestayAvailabilityEntity, HomestayAvailabilityKey> {
}
