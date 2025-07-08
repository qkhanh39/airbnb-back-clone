package com.khanh.airbnb.repositories;

import com.khanh.airbnb.domain.entities.HomestayEntity;
import com.khanh.airbnb.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomestayRepository extends JpaRepository<HomestayEntity, Long> {
    List<HomestayEntity> findByUserEntity(UserEntity user);
}
