package com.ssginc.secretgarden.domain.compliment.repository;

import com.ssginc.secretgarden.domain.compliment.entity.Compliment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplimentRepository extends JpaRepository<Compliment, Integer> {
    List<Compliment> findTop3ByOrderByCreatedAtDesc();

    List<Compliment> findAllByOrderByCreatedAtDesc();

    List<Compliment> findByCategoryOrderByCreatedAtDesc(String daily);
}
