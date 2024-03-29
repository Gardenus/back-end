package com.ssginc.secretgarden.domain.compliment.repository;

import com.ssginc.secretgarden.domain.compliment.dto.ComplimentRankingDto;
import com.ssginc.secretgarden.domain.compliment.entity.Compliment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComplimentRepository extends JpaRepository<Compliment, Integer> {
    List<Compliment> findTop3ByOrderByCreatedAtDesc();

    List<Compliment> findAllByOrderByCreatedAtDesc();

    List<Compliment> findByCategoryOrderByCreatedAtDesc(String daily);

    @Query("SELECT new com.ssginc.secretgarden.domain.compliment.dto.ComplimentRankingDto(c.receiverId, COUNT(c.receiverId), RANK() OVER (ORDER BY COUNT(c.receiverId) desc )) " +
            "FROM Compliment c " +
            "where month(c.createdAt) = :currentMonth " +
            "group by c.receiverId " +
            "ORDER BY COUNT(c.receiverId) desc " +
            "LIMIT 3")
    List<ComplimentRankingDto> findTop3Member(@Param("currentMonth")Integer currentMonth);

    List<Compliment> findByReceiverIdOrderByCreatedAtDesc(Integer memberId);

    List<Compliment> findByMemberIdOrderByCreatedAtDesc(Integer memberId);
}
