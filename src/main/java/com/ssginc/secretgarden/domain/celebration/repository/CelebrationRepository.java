package com.ssginc.secretgarden.domain.celebration.repository;

import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CelebrationRepository extends JpaRepository<Celebration, Integer> {
}
