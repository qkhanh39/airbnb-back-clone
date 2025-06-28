package com.khanh.airbnb.domain.repositories;

import com.khanh.airbnb.domain.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
}
