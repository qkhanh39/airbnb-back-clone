package com.khanh.airbnb.domain.repositories;

import com.khanh.airbnb.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
